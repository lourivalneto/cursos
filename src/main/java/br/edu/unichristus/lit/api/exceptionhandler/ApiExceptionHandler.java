package br.edu.unichristus.lit.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import br.edu.unichristus.lit.core.exception.BusinessException;
import br.edu.unichristus.lit.core.exception.EntityInUseException;
import br.edu.unichristus.lit.core.exception.EntityNotFoundException;
import br.edu.unichristus.lit.core.internationalization.BeanUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	public static final String GENERIC_ERROR_MESSAGE = "erro.mensagem-generica";
	public static final String INVALID_FIELDS_MESSAGE = "erro.campos-invalidos";
	public static final String NON_EXISTENT_RESOURCE_MESSAGE = "erro.recurso-inexistente";
	public static final String INVALID_PARAMETER_TYPE_MESSAGE = "erro.parametro-tipo-invalido";
	public static final String INVALID_REQUEST_MESSAGE = "erro.requisicao-invalida";
	public static final String NON_EXISTENT_PROPERTY_MESSAGE = "erro.propriedade-inexistente";
	public static final String INVALID_PROPERTY_TYPE_MESSAGE = "erro.propriedade-tipo-invalido";

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(final HttpMediaTypeNotAcceptableException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		return ResponseEntity.status(status).headers(headers).build();
	}

	@Override
	protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		return this.handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		return this.handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
	}

	private ResponseEntity<Object> handleValidationInternal(final Exception ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request, final BindingResult bindingResult) {
		final ProblemType problemType = ProblemType.INVALID_DATA;
		final String detail = this.getMessage(INVALID_FIELDS_MESSAGE);
		final List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream().map(objectError -> {
			final String message = BeanUtils.getMessageSource().getMessage(objectError,
					LocaleContextHolder.getLocale());
			String name = objectError.getObjectName();
			if (objectError instanceof FieldError) {
				name = ((FieldError) objectError).getField();
			}
			return Problem.Object.builder().name(name).userMessage(message).build();
		}).collect(Collectors.toList());
		final Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail)
				.objects(problemObjects).build();
		return this.handleExceptionInternal(ex, problem, headers, status, request);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
		final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		final ProblemType problemType = ProblemType.SYSTEM_ERROR;
		final String detail = this.getMessage(GENERIC_ERROR_MESSAGE);
		log.error(ex.getMessage(), ex);
		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();
		return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		final ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
		final String detail = this.getMessage(NON_EXISTENT_RESOURCE_MESSAGE, ex.getRequestURL());
		final Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(this.getMessage(GENERIC_ERROR_MESSAGE)).build();
		return this.handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		if (ex instanceof MethodArgumentTypeMismatchException) {
			return this.handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status,
					request);
		}
		return super.handleTypeMismatch(ex, headers, status, request);
	}

	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		final ProblemType problemType = ProblemType.INVALID_PARAMETER;
		final String detail = this.getMessage(INVALID_PARAMETER_TYPE_MESSAGE, ex.getName(), ex.getValue().toString(),
				ex.getRequiredType().getSimpleName());
		final Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(this.getMessage(GENERIC_ERROR_MESSAGE)).build();
		return this.handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		final Throwable rootCause = ExceptionUtils.getRootCause(ex);
		if (rootCause instanceof InvalidFormatException) {
			return this.handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
		} else if (rootCause instanceof PropertyBindingException) {
			return this.handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
		}
		final ProblemType problemType = ProblemType.INCOMPREHENSIVE_MESSAGE;
		final String detail = this.getMessage(INVALID_REQUEST_MESSAGE);
		final Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(this.getMessage(GENERIC_ERROR_MESSAGE)).build();
		return this.handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handlePropertyBinding(final PropertyBindingException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		final String path = joinPath(ex.getPath());
		final ProblemType problemType = ProblemType.INCOMPREHENSIVE_MESSAGE;
		final String detail = this.getMessage(NON_EXISTENT_PROPERTY_MESSAGE, path);
		final Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(this.getMessage(GENERIC_ERROR_MESSAGE)).build();
		return this.handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormat(final InvalidFormatException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		final String path = joinPath(ex.getPath());
		final ProblemType problemType = ProblemType.INCOMPREHENSIVE_MESSAGE;
		final String detail = this.getMessage(INVALID_PROPERTY_TYPE_MESSAGE, path, ex.getValue().toString(),
				ex.getTargetType().getSimpleName());
		final Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(this.getMessage(GENERIC_ERROR_MESSAGE)).build();
		return this.handleExceptionInternal(ex, problem, headers, status, request);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleEntityNotFound(final EntityNotFoundException ex, final WebRequest request) {
		final HttpStatus status = HttpStatus.NOT_FOUND;
		final ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
		final String detail = ex.getMessage();
		final Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();
		return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntityInUseException.class)
	public ResponseEntity<?> handleEntityInUse(final EntityInUseException ex, final WebRequest request) {
		final HttpStatus status = HttpStatus.CONFLICT;
		final ProblemType problemType = ProblemType.ENTITY_IN_USE;
		final String detail = ex.getMessage();
		final Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();
		return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<?> handleBusinessException(final BusinessException ex, final WebRequest request) {
		final HttpStatus status = HttpStatus.BAD_REQUEST;
		final ProblemType problemType = ProblemType.BUSINESS_ERROR;
		final String detail = ex.getMessage();
		final Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();
		return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(final Exception ex, Object body, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		if (body == null) {
			body = Problem.builder().timestamp(OffsetDateTime.now()).title(status.getReasonPhrase())
					.status(status.value()).userMessage(this.getMessage(GENERIC_ERROR_MESSAGE)).build();
		} else if (body instanceof String) {
			body = Problem.builder().timestamp(OffsetDateTime.now()).title((String) body).status(status.value())
					.userMessage(this.getMessage(GENERIC_ERROR_MESSAGE)).build();
		}
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private Problem.ProblemBuilder createProblemBuilder(final HttpStatus status, final ProblemType problemType,
			final String detail) {
		return Problem.builder().timestamp(OffsetDateTime.now()).status(status.value())
				.title(this.getMessage(problemType.getTitle())).detail(detail);
	}

	private String joinPath(final List<Reference> references) {
		return references.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
	}

	private String getMessage(final String text, final String... args) {
		return BeanUtils.getMessageSource().getMessage(text, args, LocaleContextHolder.getLocale());
	}

}
