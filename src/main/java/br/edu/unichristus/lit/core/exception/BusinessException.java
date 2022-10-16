package br.edu.unichristus.lit.core.exception;

import java.util.Objects;

import org.springframework.context.i18n.LocaleContextHolder;

import br.edu.unichristus.lit.core.internationalization.BeanUtils;


public class BusinessException extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1064276922168392710L;

	public BusinessException(final Throwable cause, final String message, final String... args) {
        super(getFinalMessage(message, args), cause);
        if(Objects.nonNull(cause)) {
        	cause.printStackTrace();
        }
    }
	
	private static String getFinalMessage(final String message, final String... args) {
		return BeanUtils.getMessageSource().getMessage(message, args, LocaleContextHolder.getLocale());
	}
	
}
