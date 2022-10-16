package br.edu.unichristus.lit.domain.exception.alreadyexists;

import br.edu.unichristus.lit.core.exception.ConflictException;

public class UnidadeJaCadastradaException extends ConflictException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 702772210251631801L;
	private static final String MESSAGE = "unidade.ja-cadastrada.exception";

	public UnidadeJaCadastradaException(final Throwable causa, final String nome) {
		super(causa, MESSAGE, nome);
	}

}
