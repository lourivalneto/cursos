package br.edu.unichristus.lit.api.v1.assembler.input;

import org.springframework.stereotype.Component;

import br.edu.unichristus.lit.api.v1.model.input.UnidadeInput;
import br.edu.unichristus.lit.domain.model.Unidade;
import br.edu.unichristus.lit.infrastructure.disassembler.InputDisassembler;

@Component
public class UnidadeDisassembler extends InputDisassembler<Unidade, UnidadeInput> {

}
