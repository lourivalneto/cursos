package br.edu.unichristus.lit.api.v1.controller;

import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import br.edu.unichristus.lit.api.v1.assembler.input.CursoDisassembler;
import br.edu.unichristus.lit.api.v1.assembler.output.CursoAssembler;
import br.edu.unichristus.lit.api.v1.model.input.CursoInput;
import br.edu.unichristus.lit.api.v1.model.output.CursoModel;
import br.edu.unichristus.lit.core.data.PageWrapper;
import br.edu.unichristus.lit.domain.filter.CursoFilter;
import br.edu.unichristus.lit.domain.model.Curso;
import br.edu.unichristus.lit.domain.repository.CursoRepository;
import br.edu.unichristus.lit.domain.service.CursoService;
import br.edu.unichristus.lit.infrastructure.repository.specs.CursoSpecs;

@RestController
@RequestMapping(path = "/v1/cursos")
public class CursoController {

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private CursoService cursoService;

	@Autowired
	private CursoAssembler cursoAssembler;

	@Autowired
	private CursoDisassembler cursoDisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Curso> pagedResourcesAssembler;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CollectionModel<CursoModel>> list(final CursoFilter filter, @PageableDefault(size = 10) final Pageable pageable, final ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		String eTag = "0";
		final OffsetDateTime dataUltimaAtualizacao = this.cursoRepository.getDataUltimaAtualizacao();
		if (dataUltimaAtualizacao != null) {
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}
		if (request.checkNotModified(eTag)) {
			return null;
		}
		final Page<Curso> pedidosPage = new PageWrapper<>(this.cursoRepository.findAll(
				CursoSpecs.usingFilter(filter), pageable), pageable);
		final PagedModel<CursoModel> cursosPagedModel = this.pagedResourcesAssembler.toModel(pedidosPage, cursoAssembler);
		return ResponseEntity.ok().cacheControl(CacheControl.maxAge(3, TimeUnit.DAYS).cachePublic()).eTag(eTag).body(cursosPagedModel);
	}

	@GetMapping(value = "/{cursoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CursoModel> search(@PathVariable final Long cursoId, final ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		String eTag = "0";
		final OffsetDateTime dataAtualizacao = this.cursoRepository.getDataAtualizacaoById(cursoId);
		if (dataAtualizacao != null) {
			eTag = String.valueOf(dataAtualizacao.toEpochSecond());
		}
		if (request.checkNotModified(eTag)) {
			return null;
		}
		final Curso curso = this.cursoService.fetchOrFail(cursoId);
		final CursoModel cursoModel = this.cursoAssembler.toModel(curso);
		return ResponseEntity.ok().cacheControl(CacheControl.maxAge(3, TimeUnit.DAYS)).eTag(eTag).body(cursoModel);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public CursoModel add(@RequestBody @Valid final CursoInput cursoInput) {
		Curso curso = this.cursoDisassembler.toDomainObject(cursoInput);
		curso = this.cursoService.save(curso);
		return this.cursoAssembler.toModel(curso);
	}

	@PutMapping(value = "/{cursoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CursoModel update(@PathVariable final Long cursoId, @RequestBody @Valid final CursoInput cursoInput) {
		Curso cursoAtual = this.cursoService.fetchOrFail(cursoId);
		this.cursoDisassembler.copyToDomainObject(cursoInput, cursoAtual);
		cursoAtual = this.cursoService.save(cursoAtual);
		return this.cursoAssembler.toModel(cursoAtual);
	}

	@DeleteMapping("/{cursoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable final Long cursoId) {
		this.cursoService.remove(cursoId);
	}

}
