package br.edu.unichristus.lit.api.v1.assembler.output;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.edu.unichristus.lit.api.v1.CursosLinks;
import br.edu.unichristus.lit.api.v1.controller.NivelController;
import br.edu.unichristus.lit.api.v1.model.output.NivelModel;
import br.edu.unichristus.lit.domain.model.Nivel;

@Component
public class NivelAssembler extends RepresentationModelAssemblerSupport<Nivel, NivelModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CursosLinks cursosLinks;

	public NivelAssembler() {
		super(NivelController.class, NivelModel.class);
	}

	@Override
	public NivelModel toModel(final Nivel nivel) {
		final NivelModel nivelModel = createModelWithId(nivel.getId(), nivel);
		this.modelMapper.map(nivel, nivelModel);
		return nivelModel;
	}

	@Override
	public CollectionModel<NivelModel> toCollectionModel(final Iterable<? extends Nivel> entities) {
		final CollectionModel<NivelModel> collectionModel = super.toCollectionModel(entities);
		collectionModel.add(this.cursosLinks.linkToNiveis());
		return collectionModel;
	}

}
