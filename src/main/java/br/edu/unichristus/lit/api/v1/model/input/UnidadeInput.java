package br.edu.unichristus.lit.api.v1.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.edu.unichristus.lit.api.v1.model.input.id.UsuarioIdInput;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UnidadeInput {

	@NotBlank
	private String nome;
	
	@NotNull
	private Boolean ativo;
	
	@NotNull
	private UsuarioIdInput user;

}
