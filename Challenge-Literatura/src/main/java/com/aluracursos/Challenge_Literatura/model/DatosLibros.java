package com.aluracursos.Challenge_Literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

/*@Entity
@Table(name = "Libros")*/
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(@JsonAlias("id") Long Id,
                          @JsonAlias("title") String Titulo,
                          @JsonAlias("languages") String[] Idioma,
                          @JsonAlias("authors") DatosAutor[] Autores,
                          @JsonAlias("download_count") Integer Descargas,
                          @JsonAlias("summaries") String[] Resumen,
                          @JsonAlias("formats") Formatos formatos)
{}
