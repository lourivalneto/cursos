package br.edu.unichristus.lit.api.v1.model.input.id;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NivelIdInput {

	@NotNull
	private Long id;

}
