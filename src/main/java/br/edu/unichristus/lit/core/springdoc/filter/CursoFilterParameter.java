package br.edu.unichristus.lit.core.springdoc.filter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Parameter(
        in = ParameterIn.QUERY,
        name = "unidadeId",
        description = "ID da Unidade",
        schema = @Schema(type = "integer"),
        allowEmptyValue = true,
        examples = {
                @ExampleObject("1")
        }
)
@Parameter(
        in = ParameterIn.QUERY,
        name = "nivelId",
        description = "ID do Nível",
        schema = @Schema(type = "integer"),
        allowEmptyValue = true,
        examples = {
                @ExampleObject("1")
        }
)
@Parameter(
        in = ParameterIn.QUERY,
        name = "nome",
        description = "Nome ou parte do nome do Curso.",
        allowEmptyValue = true,
        examples = {
                @ExampleObject("medicina"),
                @ExampleObject("Medicina"),
                @ExampleObject("medic"),
                @ExampleObject("Med")
        }
)
@Parameter(
        in = ParameterIn.QUERY,
        name = "ativo",
        description = "Filtrar por ativo, inativo ou todos: propriedade(true|false|null)",
        allowEmptyValue = true,
        examples = {
                @ExampleObject("true"),
                @ExampleObject("false"),
                @ExampleObject("null")
        }
)
public @interface CursoFilterParameter {
}
