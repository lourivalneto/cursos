package br.edu.unichristus.lit.domain.filter;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CursoFilter {
	
	private Long unidadeId;
	
	private Long nivelId;
	
	private String nome;
	
	private Boolean ativo;
	
	
}
