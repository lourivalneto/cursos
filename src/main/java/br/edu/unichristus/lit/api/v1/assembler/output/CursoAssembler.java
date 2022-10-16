package br.edu.unichristus.lit.api.v1.assembler.output;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.edu.unichristus.lit.api.v1.controller.CursoController;
import br.edu.unichristus.lit.api.v1.model.output.CursoModel;
import br.edu.unichristus.lit.domain.model.Curso;

@Component
public class CursoAssembler extends RepresentationModelAssemblerSupport<Curso, CursoModel> {

	@Autowired
	private ModelMapper modelMapper;

	public CursoAssembler() {
		super(CursoController.class, CursoModel.class);
	}

	@Override
	public CursoModel toModel(final Curso curso) {
		final CursoModel cursoModel = createModelWithId(curso.getId(), curso);
		this.modelMapper.map(curso, cursoModel);		
		return cursoModel;
	}

}
