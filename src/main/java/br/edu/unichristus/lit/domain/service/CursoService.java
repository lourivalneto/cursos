package br.edu.unichristus.lit.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.unichristus.lit.domain.exception.alreadyexists.CursoJaCadastradoException;
import br.edu.unichristus.lit.domain.exception.inuse.CursoEmUsoException;
import br.edu.unichristus.lit.domain.exception.notfound.CursoNaoEncontradoException;
import br.edu.unichristus.lit.domain.model.Curso;
import br.edu.unichristus.lit.domain.model.Nivel;
import br.edu.unichristus.lit.domain.model.Unidade;
import br.edu.unichristus.lit.domain.repository.CursoRepository;

@Service
public class CursoService {

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private UnidadeService unidadeService;

	@Autowired
	private NivelService nivelService;

	@Transactional
	public Curso save(final Curso curso) {
		try {
			final Unidade unidade = this.unidadeService.fetchOrFail(curso.getUnidade().getId());
			final Nivel nivel = this.nivelService.fetchOrFail(curso.getNivel().getId());
			curso.setUnidade(unidade);
			curso.setNivel(nivel);
			return this.cursoRepository.saveAndFlush(curso);
		} catch (DataIntegrityViolationException e) {
			throw new CursoJaCadastradoException(e, curso.getNome(), curso.getUnidade().getNome(),
					curso.getNivel().getNome());
		}
	}

	@Transactional
	public void remove(final Long cursoId) {
		try {
			this.cursoRepository.deleteById(cursoId);
			this.cursoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new CursoNaoEncontradoException(e);
		} catch (DataIntegrityViolationException e) {
			throw new CursoEmUsoException(e);
		}
	}

	public Curso fetchOrFail(final Long cursoId) {
		return this.cursoRepository.findById(cursoId).orElseThrow(() -> new CursoNaoEncontradoException(null));
	}

}
