package br.edu.unichristus.lit.domain.repository;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.unichristus.lit.domain.model.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long>, JpaSpecificationExecutor<Curso> {

	@Query("select max(dataAtualizacao) from Curso")
	OffsetDateTime getDataUltimaAtualizacao();

	@Query("select dataAtualizacao from Curso where id = :cursoId")
	OffsetDateTime getDataAtualizacaoById(Long cursoId);

}
