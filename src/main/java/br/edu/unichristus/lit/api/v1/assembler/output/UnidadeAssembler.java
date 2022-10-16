package br.edu.unichristus.lit.api.v1.assembler.output;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.edu.unichristus.lit.api.v1.CursosLinks;
import br.edu.unichristus.lit.api.v1.controller.UnidadeController;
import br.edu.unichristus.lit.api.v1.model.output.UnidadeModel;
import br.edu.unichristus.lit.domain.model.Unidade;

@Component
public class UnidadeAssembler extends RepresentationModelAssemblerSupport<Unidade, UnidadeModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CursosLinks cursosLinks;

	public UnidadeAssembler() {
		super(UnidadeController.class, UnidadeModel.class);
	}

	@Override
	public UnidadeModel toModel(final Unidade unidade) {
		final UnidadeModel unidadeModel = createModelWithId(unidade.getId(), unidade);
		this.modelMapper.map(unidade, unidadeModel);
		return unidadeModel;
	}

	@Override
	public CollectionModel<UnidadeModel> toCollectionModel(final Iterable<? extends Unidade> entities) {
		final CollectionModel<UnidadeModel> collectionModel = super.toCollectionModel(entities);
		collectionModel.add(this.cursosLinks.linkToUnidades());
		return collectionModel;
	}

}
