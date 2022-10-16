package br.edu.unichristus.lit.api.v1.assembler.input;

import org.springframework.stereotype.Component;

import br.edu.unichristus.lit.api.v1.model.input.CursoInput;
import br.edu.unichristus.lit.domain.model.Curso;
import br.edu.unichristus.lit.domain.model.Nivel;
import br.edu.unichristus.lit.domain.model.Unidade;
import br.edu.unichristus.lit.infrastructure.disassembler.InputDisassembler;

@Component
public class CursoDisassembler extends InputDisassembler<Curso, CursoInput> {

	@Override
	public void copyToDomainObject(CursoInput input, Curso domain) {
		domain.setNivel(new Nivel());
		domain.setUnidade(new Unidade());
		super.copyToDomainObject(input, domain);
	}

}
