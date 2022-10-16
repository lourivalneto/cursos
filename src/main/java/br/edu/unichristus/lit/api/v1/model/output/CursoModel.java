package br.edu.unichristus.lit.api.v1.model.output;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cursos")
@Setter
@Getter
public class CursoModel extends RepresentationModel<CursoModel> {

	private Long id;

	private String nome;

	private UnidadeModel unidade;

	private NivelModel nivel;

	private Boolean ativo;

}
