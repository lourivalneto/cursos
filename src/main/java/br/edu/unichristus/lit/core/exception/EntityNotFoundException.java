package br.edu.unichristus.lit.core.exception;

public abstract class EntityNotFoundException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5514386558428851172L;

	public EntityNotFoundException(final Throwable cause, final String message, final String... args) {
		super(cause, message, args);
	}
	
}
