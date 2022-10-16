package br.edu.unichristus.lit.domain.repository;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.unichristus.lit.domain.model.Unidade;
import br.edu.unichristus.lit.infrastructure.repository.CustomJpaRepository;

@Repository
public interface UnidadeRepository extends CustomJpaRepository<Unidade, Long> {
	
	@Query("select max(dataAtualizacao) from Unidade")
	OffsetDateTime getDataUltimaAtualizacao();
	
	@Query("select dataAtualizacao from Unidade where id = :unidadeId")
	OffsetDateTime getDataAtualizacaoById(Long unidadeId);
	
	Optional<Unidade> findByNomeIgnoreCase(String nome);

}
