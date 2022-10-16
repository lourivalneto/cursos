package br.edu.unichristus.lit.domain.exception.inuse;

import br.edu.unichristus.lit.core.exception.EntityInUseException;

public class CursoEmUsoException extends EntityInUseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5730645509589664695L;

	private static final String MESSAGE = "curso.em-uso.exception";

	public CursoEmUsoException(final Throwable causa) {
		super(causa, MESSAGE);
	}

}
