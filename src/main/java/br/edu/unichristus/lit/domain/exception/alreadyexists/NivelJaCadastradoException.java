package br.edu.unichristus.lit.domain.exception.alreadyexists;

import br.edu.unichristus.lit.core.exception.ConflictException;

public class NivelJaCadastradoException extends ConflictException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6732293384308470841L;

	private static final String MESSAGE = "nivel.ja-cadastrado.exception";

	public NivelJaCadastradoException(final Throwable causa, final String nome) {
		super(causa, MESSAGE, nome);
	}

}
