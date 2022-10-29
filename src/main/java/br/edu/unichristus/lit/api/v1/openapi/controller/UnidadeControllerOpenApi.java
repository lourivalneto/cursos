package br.edu.unichristus.lit.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import br.edu.unichristus.lit.api.v1.model.input.UnidadeInput;
import br.edu.unichristus.lit.api.v1.model.output.UnidadeModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Unidades")
public interface UnidadeControllerOpenApi {

	@Operation(summary = "Lista as Unidades")
	public ResponseEntity<CollectionModel<UnidadeModel>> listar(final ServletWebRequest request);

	@Operation(summary = "Busca uma Unidade por id")
	public ResponseEntity<UnidadeModel> buscar(final Long unidadeId, final ServletWebRequest request);

	@Operation(summary = "Adiciona uma nova Unidade")
	public UnidadeModel adicionar(final UnidadeInput unidadeInput);

	@Operation(summary = "Atualiza uma Undiade")
	public UnidadeModel atualizar(final Long unidadeId, final UnidadeInput unidadeInput);

	@Operation(summary = "Remove uma Unidade")
	public void remover(final Long unidadeId);

}
