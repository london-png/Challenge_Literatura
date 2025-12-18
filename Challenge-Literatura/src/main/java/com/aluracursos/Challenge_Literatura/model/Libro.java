package com.aluracursos.Challenge_Literatura.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros") // creacion de la tabla libros
public class Libro {

    @Id// clave primaria, identificador unico de cada libro
    private Long id;

    //  Especificar que titulo puede ser largo
    @Column(columnDefinition = "TEXT") // se usa para cuando el varchar tiene mas de 255 caracteres
    private String titulo;

    private String[] idioma; // por que los libros pueden estar en mas de un idioma

    private Integer descargas;

    // Especificar que resumen es un array de TEXT
    @Column(columnDefinition = "TEXT[]")
    private String[] resumen;

    //  Especificar que formatos es TEXT (sin límite de 255 caracteres)
    @Column(columnDefinition = "TEXT")
    private String formatos;

    //Indica que el campo no es una entidad, sino una colección de valores simples
    @ElementCollection(fetch = FetchType.EAGER)// EAGER carga los libros de inmediato
    @CollectionTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id")
    )
    @Column(name = "autor_id")
    private List<Long> autorIds = new ArrayList<>();

    //se agrega el campo para consultas de la fecha y la hora
    @Column(name = "fecha_consulta")
    private LocalDateTime fechaConsulta;

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

    //se agrega el Getter y setter para la fecha y hora para cuando se realice la consulta


    public LocalDateTime getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(LocalDateTime fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    //metodo para formatear la fecha
    public String getFechaConsultaFormateada() {
        if (fechaConsulta == null) {
            return "No registrada";
        }
        return fechaConsulta.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    @Override // se usa para sobreescribir un metodo que ya existe en la clase padre

    //Este metodo toString() que convierte un objeto libro en un texto legible
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", idioma=" + java.util.Arrays.toString(idioma) +
                ", autorIds=" + autorIds +
                ", descargas=" + descargas +
                ", resumen=" + java.util.Arrays.toString(resumen) +
                ", formatos='" + formatos + '\'' +
                ", fechaConsulta='" + getFechaConsultaFormateada() + '\'' +
                '}';
    }
}