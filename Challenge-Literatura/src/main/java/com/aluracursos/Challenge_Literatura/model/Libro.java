package com.aluracursos.Challenge_Literatura.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    private Long id;

    // 👇 Especificar que titulo puede ser largo
    @Column(columnDefinition = "TEXT")
    private String titulo;

    private String[] idioma;

    private Integer descargas;

    // 👇 Especificar que resumen es un array de TEXT
    @Column(columnDefinition = "TEXT[]")
    private String[] resumen;

    // 👇 Especificar que formatos es TEXT (sin límite de 255 caracteres)
    @Column(columnDefinition = "TEXT")
    private String formatos;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id")
    )
    @Column(name = "autor_id")
    private List<Long> autorIds = new ArrayList<>();

    // Constructor vacío
    public Libro() {}

    // Getters y Setters (sin cambios)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String[] getIdioma() { return idioma; }
    public void setIdioma(String[] idioma) { this.idioma = idioma; }

    public Integer getDescargas() { return descargas; }
    public void setDescargas(Integer descargas) { this.descargas = descargas; }

    public String[] getResumen() { return resumen; }
    public void setResumen(String[] resumen) { this.resumen = resumen; }

    public String getFormatos() { return formatos; }
    public void setFormatos(String formatos) { this.formatos = formatos; }

    public List<Long> getAutorIds() { return autorIds; }
    public void setAutorIds(List<Long> autorIds) { this.autorIds = autorIds; }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", idioma=" + java.util.Arrays.toString(idioma) +
                ", autorIds=" + autorIds +
                ", descargas=" + descargas +
                ", resumen=" + java.util.Arrays.toString(resumen) +
                ", formatos='" + formatos + '\'' +
                '}';
    }
}