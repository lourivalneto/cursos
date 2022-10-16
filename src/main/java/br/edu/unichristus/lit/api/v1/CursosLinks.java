package br.edu.unichristus.lit.api.v1;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

import br.edu.unichristus.lit.api.v1.controller.CursoController;
import br.edu.unichristus.lit.api.v1.controller.NivelController;
import br.edu.unichristus.lit.api.v1.controller.UnidadeController;

@Component
public class CursosLinks {

	public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("page", VariableType.REQUEST_PARAM),
			new TemplateVariable("size", VariableType.REQUEST_PARAM),
			new TemplateVariable("sort", VariableType.REQUEST_PARAM));

	public Link linkToCursos(String rel) {
		TemplateVariables filtroVariables = new TemplateVariables(
				new TemplateVariable("unidadeId", VariableType.REQUEST_PARAM),
				new TemplateVariable("nivelId", VariableType.REQUEST_PARAM),
				new TemplateVariable("nome", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataInicioVigencia", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataFimVigencia", VariableType.REQUEST_PARAM));

		String pedidosUrl = linkTo(CursoController.class).toUri().toString();

		return Link.of(UriTemplate.of(pedidosUrl, PAGINACAO_VARIABLES.concat(filtroVariables)), rel);
	}
	
	public Link linkToUnidades() {
		return linkToUnidades("unidades");
	}
	
	public Link linkToUnidades(String rel) {
		return linkTo(UnidadeController.class).withRel(rel);
	}
	
	public Link linkToNiveis() {
		return linkToNiveis("niveis");
	}
	
	public Link linkToNiveis(String rel) {
		return linkTo(NivelController.class).withRel(rel);
	}

}
