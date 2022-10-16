package br.edu.unichristus.lit.infrastructure.repository.specs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import br.edu.unichristus.lit.domain.filter.CursoFilter;
import br.edu.unichristus.lit.domain.model.Curso;

public class CursoSpecs {

	public static Specification<Curso> usingFilter(CursoFilter filtro) {
		return (root, query, builder) -> {
			if (Curso.class.equals(query.getResultType())) {
				root.fetch("unidade");
				root.fetch("nivel");
			}
			var predicates = new ArrayList<Predicate>();
			if (Objects.nonNull(filtro.getUnidadeId())) {
				predicates.add(builder.equal(root.get("unidade"), filtro.getUnidadeId()));
			}
			if (Objects.nonNull(filtro.getNivelId())) {
				predicates.add(builder.equal(root.get("nivel"), filtro.getNivelId()));
			}
			if (StringUtils.hasText(filtro.getNome())) {
				predicates.add(builder.like(root.get("nome"), String.format("%%%s%%", filtro.getNome())));
			}
			if (Objects.nonNull(filtro.getAtivo())) {
				predicates.add(builder.equal(root.get("ativo"), filtro.getAtivo()));
			}
			query.orderBy(Arrays.asList(builder.asc(root.get("nome")), builder.asc(root.get("unidade").get("nome")),
					builder.asc(root.get("nivel").get("nome"))));
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
