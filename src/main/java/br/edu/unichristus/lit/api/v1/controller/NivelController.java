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

import br.edu.unichristus.lit.api.v1.assembler.input.NivelDisassembler;
import br.edu.unichristus.lit.api.v1.assembler.output.NivelAssembler;
import br.edu.unichristus.lit.api.v1.model.input.NivelInput;
import br.edu.unichristus.lit.api.v1.model.output.NivelModel;
import br.edu.unichristus.lit.api.v1.openapi.controller.NivelControllerOpenApi;
import br.edu.unichristus.lit.domain.model.Nivel;
import br.edu.unichristus.lit.domain.repository.NivelRepository;
import br.edu.unichristus.lit.domain.service.NivelService;

@RestController
@RequestMapping(path = "/v1/niveis")
public class NivelController implements NivelControllerOpenApi {

	@Autowired
	private NivelRepository nivelRepository;

	@Autowired
	private NivelService nivelService;

	@Autowired
	private NivelAssembler nivelAssembler;

	@Autowired
	private NivelDisassembler nivelDisassembler;

	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CollectionModel<NivelModel>> listar(final ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		String eTag = "0";
		final OffsetDateTime dataUltimaAtualizacao = this.nivelRepository.getDataUltimaAtualizacao();
		if (dataUltimaAtualizacao != null) {
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}
		if (request.checkNotModified(eTag)) {
			return null;
		}
		final List<Nivel> todosNiveis = this.nivelRepository.findAll();
		final CollectionModel<NivelModel> niveisModel = this.nivelAssembler.toCollectionModel(todosNiveis);
		return ResponseEntity.ok().cacheControl(CacheControl.maxAge(3, TimeUnit.DAYS).cachePublic()).eTag(eTag)
				.body(niveisModel);
	}

	@Override
	@GetMapping(value = "/{nivelId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NivelModel> buscar(@PathVariable final Long nivelId, final ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		String eTag = "0";
		final OffsetDateTime dataAtualizacao = this.nivelRepository.getDataAtualizacaoById(nivelId);
		if (dataAtualizacao != null) {
			eTag = String.valueOf(dataAtualizacao.toEpochSecond());
		}
		if (request.checkNotModified(eTag)) {
			return null;
		}
		final Nivel nivel = this.nivelService.fetchOrFail(nivelId);
		final NivelModel nivelModel = this.nivelAssembler.toModel(nivel);
		return ResponseEntity.ok().cacheControl(CacheControl.maxAge(3, TimeUnit.DAYS)).eTag(eTag).body(nivelModel);
	}

	@Override
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public NivelModel adicionar(@RequestBody @Valid final NivelInput nivelInput) {
		Nivel nivel = this.nivelDisassembler.toDomainObject(nivelInput);
		nivel = this.nivelService.salvar(nivel);
		return this.nivelAssembler.toModel(nivel);
	}

	@Override
	@PutMapping(value = "/{nivelId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public NivelModel atualizar(@PathVariable final Long nivelId, @RequestBody @Valid final NivelInput nivelInput) {
		Nivel nivelAtual = this.nivelService.fetchOrFail(nivelId);
		this.nivelRepository.detach(nivelAtual);
		this.nivelDisassembler.copyToDomainObject(nivelInput, nivelAtual);
		nivelAtual = this.nivelService.salvar(nivelAtual);
		return this.nivelAssembler.toModel(nivelAtual);
	}

	@Override
	@DeleteMapping("/{nivelId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable final Long nivelId) {
		this.nivelService.excluir(nivelId);
	}

}
