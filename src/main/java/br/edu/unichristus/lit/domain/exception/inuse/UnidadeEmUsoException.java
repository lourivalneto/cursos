package br.edu.unichristus.lit.domain.exception.inuse;

import br.edu.unichristus.lit.core.exception.EntityInUseException;

public class UnidadeEmUsoException extends EntityInUseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9216632187262387863L;

	private static final String MESSAGE = "unidade.em-uso.exception";

	public UnidadeEmUsoException(final Throwable causa) {
		super(causa, MESSAGE);
	}

}
