package br.edu.unichristus.lit.infrastructure.disassembler;

import java.lang.reflect.ParameterizedType;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class InputDisassembler<D, I> {

	protected Class<D> classe;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@SuppressWarnings("unchecked")
	public InputDisassembler() {
		this.classe = (Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public D toDomainObject(final I input) {
		return this.modelMapper.map(input, classe);
	}
	
	public void copyToDomainObject(final I input, final D domain) {
		this.modelMapper.map(input, domain);
	}
	
	protected ModelMapper getModelMapper() {
		return modelMapper;
	}
}
