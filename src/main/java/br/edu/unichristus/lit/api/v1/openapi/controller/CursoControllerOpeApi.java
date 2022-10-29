package br.edu.unichristus.lit.api.v1.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import br.edu.unichristus.lit.api.v1.model.input.CursoInput;
import br.edu.unichristus.lit.api.v1.model.output.CursoModel;
import br.edu.unichristus.lit.core.springdoc.PageableParameter;
import br.edu.unichristus.lit.core.springdoc.filter.CursoFilterParameter;
import br.edu.unichristus.lit.domain.filter.CursoFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cursos")
public interface CursoControllerOpeApi {

	@Operation(summary = "Lista os Cursos")
	@PageableParameter
	@CursoFilterParameter
	public ResponseEntity<CollectionModel<CursoModel>> list(final @Parameter(hidden = true) CursoFilter filter, final @Parameter(hidden = true) Pageable pageable,
			final ServletWebRequest request);

	@Operation(summary = "Busca um Curso por id")
	public ResponseEntity<CursoModel> search(final Long cursoId, final ServletWebRequest request);

	@Operation(summary = "Adiciona um novo Curso")
	public CursoModel add(final CursoInput cursoInput);

	@Operation(summary = "Atualiza um Curso")
	public CursoModel update(final Long cursoId, final CursoInput cursoInput);

	@Operation(summary = "Remove um Curso")
	public void remove(final Long cursoId);
}
