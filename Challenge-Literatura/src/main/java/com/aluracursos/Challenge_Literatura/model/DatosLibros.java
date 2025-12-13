package com.aluracursos.Challenge_Literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(@JsonAlias("id") Long Id,
                          @JsonAlias("title") String titulo,
                          @JsonAlias("languages") String[] Idioma,
                          @JsonAlias("authors") DatosAutor[] Autores,
                          @JsonAlias("download_count") Integer Descargas,
                          @JsonAlias("summaries") String[] Resumen,
                          @JsonAlias("formats") Formatos formatos)
{}
