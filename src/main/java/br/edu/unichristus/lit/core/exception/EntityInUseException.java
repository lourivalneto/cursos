package br.edu.unichristus.lit.core.exception;

public abstract class EntityInUseException extends ConflictException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3571141963693652203L;

	public EntityInUseException(final Throwable cause, final String message, final String... args) {
		super(cause, message, args);
	}
	
}
