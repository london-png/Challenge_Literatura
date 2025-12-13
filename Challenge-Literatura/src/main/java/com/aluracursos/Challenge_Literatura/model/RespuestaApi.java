package com.aluracursos.Challenge_Literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RespuestaApi(
        @JsonAlias("count") int total,
        @JsonAlias("next") String siguientePagina,
        @JsonAlias("previous") String paginaAnterior,
        @JsonAlias("results") DatosLibros[] resultados)
{ }
