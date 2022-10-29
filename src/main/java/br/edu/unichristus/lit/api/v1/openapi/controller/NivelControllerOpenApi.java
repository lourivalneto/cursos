package br.edu.unichristus.lit.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import br.edu.unichristus.lit.api.v1.model.input.NivelInput;
import br.edu.unichristus.lit.api.v1.model.output.NivelModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Níveis")
public interface NivelControllerOpenApi {

	@Operation(summary = "Lista os Níveis")
	public ResponseEntity<CollectionModel<NivelModel>> listar(final ServletWebRequest request);

	@Operation(summary = "Busca um Nível por id")
	public ResponseEntity<NivelModel> buscar(final Long nivelId, final ServletWebRequest request);

	@Operation(summary = "Adiciona um novo Nível")
	public NivelModel adicionar(final NivelInput nivelInput);

	@Operation(summary = "Atualiza um Nível")
	public NivelModel atualizar(final Long nivelId, final NivelInput nivelInput);

	@Operation(summary = "Remove um Nível")
	public void remover(final Long nivelId);
}
