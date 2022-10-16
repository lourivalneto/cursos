package br.edu.unichristus.lit.core.exception;

public abstract class ConflictException extends BusinessException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4657856589743859188L;

	public ConflictException(final Throwable cause, final String message, final String... args) {
		super(cause, message, args);
	}
	
}
