package br.edu.unichristus.lit.domain.exception.notfound;

import br.edu.unichristus.lit.core.exception.EntityNotFoundException;

public class UnidadeNaoEncontradaException extends EntityNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5431903433501066136L;

	private static final String MESSAGE = "unidade.nao-encontrada.exception";

	public UnidadeNaoEncontradaException(final Throwable causa) {
		super(causa, MESSAGE);
	}

}
