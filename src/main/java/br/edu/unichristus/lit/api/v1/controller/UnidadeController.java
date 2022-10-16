package br.edu.unichristus.lit.api.v1.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import br.edu.unichristus.lit.api.v1.assembler.input.UnidadeDisassembler;
import br.edu.unichristus.lit.api.v1.assembler.output.UnidadeAssembler;
import br.edu.unichristus.lit.api.v1.model.input.UnidadeInput;
import br.edu.unichristus.lit.api.v1.model.output.UnidadeModel;
import br.edu.unichristus.lit.domain.model.Unidade;
import br.edu.unichristus.lit.domain.repository.UnidadeRepository;
import br.edu.unichristus.lit.domain.service.UnidadeService;

@RestController
@RequestMapping(path = "/v1/unidades")
public class UnidadeController {

	@Autowired
	private UnidadeRepository unidadeRepository;

	@Autowired
	private UnidadeService unidadeService;

	@Autowired
	private UnidadeAssembler unidadeAssembler;

	@Autowired
	private UnidadeDisassembler unidadeDisassembler;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CollectionModel<UnidadeModel>> listar(final ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		String eTag = "0";
		final OffsetDateTime dataUltimaAtualizacao = this.unidadeRepository.getDataUltimaAtualizacao();
		if (dataUltimaAtualizacao != null) {
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}
		if (request.checkNotModified(eTag)) {
			return null;
		}
		final List<Unidade> todasUnidades = this.unidadeRepository.findAll();
		final CollectionModel<UnidadeModel> unidadesModel = this.unidadeAssembler.toCollectionModel(todasUnidades);
		return ResponseEntity.ok().cacheControl(CacheControl.maxAge(3, TimeUnit.DAYS).cachePublic()).eTag(eTag)
				.body(unidadesModel);
	}

	@GetMapping(value = "/{unidadeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UnidadeModel> buscar(@PathVariable final Long unidadeId, final ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		String eTag = "0";
		final OffsetDateTime dataAtualizacao = this.unidadeRepository.getDataAtualizacaoById(unidadeId);
		if (dataAtualizacao != null) {
			eTag = String.valueOf(dataAtualizacao.toEpochSecond());
		}
		if (request.checkNotModified(eTag)) {
			return null;
		}
		final Unidade unidade = this.unidadeService.fetchOrFail(unidadeId);
		final UnidadeModel unidadeModel = this.unidadeAssembler.toModel(unidade);
		return ResponseEntity.ok().cacheControl(CacheControl.maxAge(3, TimeUnit.DAYS)).eTag(eTag).body(unidadeModel);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public UnidadeModel adicionar(@RequestBody @Valid final UnidadeInput unidadeInput) {
		Unidade unidade = this.unidadeDisassembler.toDomainObject(unidadeInput);
		unidade = this.unidadeService.salvar(unidade);
		return this.unidadeAssembler.toModel(unidade);
	}

	@PutMapping(value = "/{unidadeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UnidadeModel atualizar(@PathVariable final Long unidadeId,
			@RequestBody @Valid final UnidadeInput unidadeInput) {
		Unidade unidadeAtual = this.unidadeService.fetchOrFail(unidadeId);
		this.unidadeRepository.detach(unidadeAtual);
		this.unidadeDisassembler.copyToDomainObject(unidadeInput, unidadeAtual);
		unidadeAtual = this.unidadeService.salvar(unidadeAtual);
		return this.unidadeAssembler.toModel(unidadeAtual);
	}

	@DeleteMapping("/{unidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable final Long unidadeId) {
		this.unidadeService.excluir(unidadeId);
	}

}
