package com.aluracursos.Challenge_Literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//ignora los campos que no se van ausar para que continue el prceso
@JsonIgnoreProperties(ignoreUnknown = true)

// es una clase de "transferencia de datos" que solo existe para recibir la información que viene de la API de Gutendex
//sirve para que Jackson (la librería de JSON) pueda convertir el JSON en un objeto Java.
public record DatosAutor(@JsonAlias("name") String Nombre,
                         @JsonAlias("birth_year") Integer Nacimiento,
                         @JsonAlias("death_year") Integer Muerte)
{}
