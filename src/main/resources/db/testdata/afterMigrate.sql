set foreign_key_checks = 0;

lock tables	lit_unidade write,
		lit_nivel write,
		lit_curso write;
		
delete from lit_unidade;
delete from lit_nivel;
delete from lit_curso;

alter table lit_unidade auto_increment = 1;
alter table lit_nivel auto_increment = 1;
alter table lit_curso auto_increment = 1;

set foreign_key_checks = 1;

INSERT INTO lit_unidade (nome, usuario_alteracao) VALUES ('Parque Ecológico', 1), ('Benfica', 1);
INSERT INTO lit_nivel (nome, usuario_alteracao) VALUES ('Graduação', 1), ('Mestrado', 1);
INSERT INTO lit_curso (nome, unidade_id, nivel_id, ativo, usuario_alteracao) VALUES ('Medicina', 1, 1, true, 1), ('Odontologia', 1, 1, true, 1), ('Odontologia', 2, 1, true, 1);

unlock tables;
