package br.edu.unichristus.lit.domain.exception.inuse;

import br.edu.unichristus.lit.core.exception.EntityInUseException;

public class NivelEmUsoException extends EntityInUseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2925140994097807763L;

	private static final String MESSAGE = "nivel.em-uso.exception";

	public NivelEmUsoException(final Throwable causa) {
		super(causa, MESSAGE);
	}

}
