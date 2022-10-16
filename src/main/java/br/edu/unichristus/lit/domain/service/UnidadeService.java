package br.edu.unichristus.lit.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.unichristus.lit.domain.exception.alreadyexists.UnidadeJaCadastradaException;
import br.edu.unichristus.lit.domain.exception.inuse.UnidadeEmUsoException;
import br.edu.unichristus.lit.domain.exception.notfound.UnidadeNaoEncontradaException;
import br.edu.unichristus.lit.domain.model.Unidade;
import br.edu.unichristus.lit.domain.repository.UnidadeRepository;

@Service
public class UnidadeService {

	@Autowired
	private UnidadeRepository unidadeRepository;

	@Transactional
	public Unidade salvar(final Unidade unidade) {
		Optional<Unidade> unidadeExistente = this.unidadeRepository.findByNomeIgnoreCase(unidade.getNome().trim());
		if (unidadeExistente.isPresent() && !unidadeExistente.get().getId().equals(unidade.getId())) {
			throw new UnidadeJaCadastradaException(null, unidade.getNome().trim());
		}
		return this.unidadeRepository.saveAndFlush(unidade);
	}

	@Transactional
	public void excluir(final Long unidadeId) {
		try {
			this.unidadeRepository.deleteById(unidadeId);
			this.unidadeRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new UnidadeNaoEncontradaException(e);
		} catch (DataIntegrityViolationException e) {
			throw new UnidadeEmUsoException(e);
		}
	}

	public Unidade fetchOrFail(final Long unidadeId) {
		return this.unidadeRepository.findById(unidadeId).orElseThrow(() -> new UnidadeNaoEncontradaException(null));
	}

}
