package br.edu.unichristus.lit.domain.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.edu.unichristus.lit.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "lit_curso", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "nome", "unidade_id", "nivel_id", }) })
public class Curso {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false, length = 60)
	private String nome;

	@Valid
	@ConvertGroup(from = Default.class, to = Groups.UnidadeId.class)
	@NotNull
	@ManyToOne
	@JoinColumn(nullable = false)
	private Unidade unidade;

	@Valid
	@ConvertGroup(from = Default.class, to = Groups.NivelId.class)
	@NotNull
	@ManyToOne
	@JoinColumn(nullable = false)
	private Nivel nivel;

	@Column(nullable = false)
	private boolean ativo;

	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime", updatable = false)
	private OffsetDateTime dataCadastro;

	@UpdateTimestamp
	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;

	@Column(name = "usuario_alteracao", nullable = false)
	private Long userId;

}
