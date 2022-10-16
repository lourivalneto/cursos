package br.edu.unichristus.lit.api.v1.model.output;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "unidades")
@Setter
@Getter
public class UnidadeModel extends RepresentationModel<UnidadeModel> {

	private Long id;

	private String nome;

}
