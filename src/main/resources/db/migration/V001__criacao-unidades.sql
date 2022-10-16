CREATE TABLE lit_unidade (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(60) NOT NULL UNIQUE,
  data_cadastro datetime NOT NULL DEFAULT (utc_timestamp),
  data_atualizacao datetime,
  usuario_alteracao INTEGER,
  
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT charset=utf8;

