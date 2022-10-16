package br.edu.unichristus.lit.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.unichristus.lit.domain.exception.alreadyexists.NivelJaCadastradoException;
import br.edu.unichristus.lit.domain.exception.inuse.NivelEmUsoException;
import br.edu.unichristus.lit.domain.exception.notfound.NivelNaoEncontradoException;
import br.edu.unichristus.lit.domain.model.Nivel;
import br.edu.unichristus.lit.domain.repository.NivelRepository;

@Service
public class NivelService {

	@Autowired
	private NivelRepository nivelRepository;

	@Transactional
	public Nivel salvar(final Nivel nivel) {
		Optional<Nivel> nivelExistente = this.nivelRepository.findByNomeIgnoreCase(nivel.getNome().trim());
		if (nivelExistente.isPresent() && !nivelExistente.get().getId().equals(nivel.getId())) {
			throw new NivelJaCadastradoException(null, nivel.getNome().trim());
		}
		return this.nivelRepository.saveAndFlush(nivel);
	}

	@Transactional
	public void excluir(final Long nivelId) {
		try {
			this.nivelRepository.deleteById(nivelId);
			this.nivelRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new NivelNaoEncontradoException(e);
		} catch (DataIntegrityViolationException e) {
			throw new NivelEmUsoException(e);
		}
	}

	public Nivel fetchOrFail(final Long nivelId) {
		return this.nivelRepository.findById(nivelId).orElseThrow(() -> new NivelNaoEncontradoException(null));
	}

}
