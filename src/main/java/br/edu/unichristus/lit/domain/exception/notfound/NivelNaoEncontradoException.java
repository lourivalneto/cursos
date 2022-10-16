package br.edu.unichristus.lit.domain.exception.notfound;

import br.edu.unichristus.lit.core.exception.EntityNotFoundException;

public class NivelNaoEncontradoException extends EntityNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7826541763380822140L;

	private static final String MESSAGE = "nivel.nao-encontrado.exception";

	public NivelNaoEncontradoException(final Throwable causa) {
		super(causa, MESSAGE);
	}

}
