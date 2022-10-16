package br.edu.unichristus.lit.domain.exception.alreadyexists;

import br.edu.unichristus.lit.core.exception.ConflictException;

public class VigenciasSobrepostasException extends ConflictException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6066589062007640522L;
	private static final String MESSAGE = "periodo.vigencias-sobrepostas.exception";

	public VigenciasSobrepostasException(final Throwable causa, final String datas) {
		super(causa, MESSAGE, datas);
	}

}
