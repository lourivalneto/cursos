package br.edu.unichristus.lit.domain.exception.notfound;

import br.edu.unichristus.lit.core.exception.EntityNotFoundException;

public class CursoNaoEncontradoException extends EntityNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2102302349323827289L;

	private static final String MESSAGE = "curso.nao-encontrado.exception";

	public CursoNaoEncontradoException(final Throwable causa) {
		super(causa, MESSAGE);
	}

}
