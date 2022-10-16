package br.edu.unichristus.lit.domain.exception.alreadyexists;

import br.edu.unichristus.lit.core.exception.ConflictException;

public class CursoJaCadastradoException extends ConflictException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4837004108983903428L;
	private static final String MESSAGE = "curso.ja-cadastrado.exception";

	public CursoJaCadastradoException(final Throwable causa, final String nomeCurso, final String nomeUnidade,
			final String nomeNivel) {
		super(causa, MESSAGE, String.format("%s [%s - %s]", nomeCurso, nomeUnidade, nomeNivel));
	}

}
