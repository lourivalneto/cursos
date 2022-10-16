CREATE TABLE lit_curso (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(60) NOT NULL,
  unidade_id BIGINT NOT NULL,
  nivel_id BIGINT NOT NULL,
  ativo boolean not null,
  data_cadastro datetime not null default (utc_timestamp),
  data_atualizacao datetime,
  usuario_alteracao INTEGER NOT NULL,
  
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT charset=utf8;

ALTER TABLE lit_curso ADD UNIQUE unique_c_n_u(nome, unidade_id, nivel_id);
ALTER TABLE lit_curso ADD CONSTRAINT fk_curso_unidade FOREIGN KEY (unidade_id) REFERENCES lit_unidade (id);
ALTER TABLE lit_curso ADD CONSTRAINT fk_curso_nivel FOREIGN KEY (nivel_id) REFERENCES lit_nivel (id);
