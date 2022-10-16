package br.edu.unichristus.lit.api.v1.assembler.input;

import org.springframework.stereotype.Component;

import br.edu.unichristus.lit.api.v1.model.input.NivelInput;
import br.edu.unichristus.lit.domain.model.Nivel;
import br.edu.unichristus.lit.infrastructure.disassembler.InputDisassembler;

@Component
public class NivelDisassembler extends InputDisassembler<Nivel, NivelInput> {

}
