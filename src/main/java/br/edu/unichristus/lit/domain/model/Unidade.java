package br.edu.unichristus.lit.domain.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "lit_unidade")
public class Unidade {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false, length = 60, unique = true)
	private String nome;

	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime", updatable = false)
	private OffsetDateTime dataCadastro;

	@UpdateTimestamp
	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;

	@Column(name = "usuario_alteracao", nullable = false)
	private Long userId;

}
