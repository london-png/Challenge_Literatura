package com.aluracursos.Challenge_Literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

//le permite a tu aplicación entender la estructura de la respuesta JSON que devuelve la API de Gutendex.
public record RespuestaApi(
        @JsonAlias("count") int total,
        @JsonAlias("next") String siguientePagina,
        @JsonAlias("previous") String paginaAnterior,
        @JsonAlias("results") DatosLibros[] resultados)
{ }
