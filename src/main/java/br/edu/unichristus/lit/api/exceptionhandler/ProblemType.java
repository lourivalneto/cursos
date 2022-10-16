package br.edu.unichristus.lit.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	INVALID_DATA("erro.titulo.dados-invalidos"),
	SYSTEM_ERROR("erro.titulo.erro-de-sistema"),
	INVALID_PARAMETER("erro.titulo.parametro-invalido"),
	INCOMPREHENSIVE_MESSAGE("erro.titulo.mensagem-incompreensivel"),
	RESOURCE_NOT_FOUND("erro.titulo.recurso-nao-encontrado"),
	ENTITY_IN_USE("erro.titulo.entidade-em-uso"),
	BUSINESS_ERROR("erro.titulo.erro-negocio");
	
	private String title;
	
	ProblemType(final String title) {
		this.title = title;
	}
	
}
