package com.aluracursos.Challenge_Literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Formatos(@JsonAlias("text/html") String html,
                       @JsonAlias("application/epub+zip") String epub,
                       @JsonAlias("application/x-mobipocket-ebook") String mobi,
                       @JsonAlias("text/plain; charset=us-ascii") String textoPlano,
                       @JsonAlias("application/rdf+xml") String rdf,
                       @JsonAlias("image/jpeg") String imagenJpeg,
                       @JsonAlias("application/octet-stream") String zip)
{}
