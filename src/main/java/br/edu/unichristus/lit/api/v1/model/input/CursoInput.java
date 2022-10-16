package br.edu.unichristus.lit.api.v1.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.edu.unichristus.lit.api.v1.model.input.id.NivelIdInput;
import br.edu.unichristus.lit.api.v1.model.input.id.UnidadeIdInput;
import br.edu.unichristus.lit.api.v1.model.input.id.UsuarioIdInput;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CursoInput {

	@NotBlank
	private String nome;

	@NotNull
	private UnidadeIdInput unidade;

	@NotNull
	private NivelIdInput nivel;
	
	@NotNull
	private Boolean ativo;
	
	@NotNull
	private UsuarioIdInput user;

}
