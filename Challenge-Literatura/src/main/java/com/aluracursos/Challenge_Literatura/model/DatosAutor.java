package com.aluracursos.Challenge_Literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(@JsonAlias("name") String Nombre,
                         @JsonAlias("birth_year") Integer Nacimiento,
                         @JsonAlias("death_year") Integer Muerte)
{}
