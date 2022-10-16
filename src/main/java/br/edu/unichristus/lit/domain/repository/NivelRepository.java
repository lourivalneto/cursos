package br.edu.unichristus.lit.domain.repository;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.unichristus.lit.domain.model.Nivel;
import br.edu.unichristus.lit.infrastructure.repository.CustomJpaRepository;

@Repository
public interface NivelRepository extends CustomJpaRepository<Nivel, Long> {
	
	@Query("select max(dataAtualizacao) from Nivel")
	OffsetDateTime getDataUltimaAtualizacao();
	
	@Query("select dataAtualizacao from Nivel where id = :nivelId")
	OffsetDateTime getDataAtualizacaoById(Long nivelId);
	
	Optional<Nivel> findByNomeIgnoreCase(String nome);

}
