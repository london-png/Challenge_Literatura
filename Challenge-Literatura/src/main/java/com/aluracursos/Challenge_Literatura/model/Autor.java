package com.aluracursos.Challenge_Literatura.model;

import jakarta.persistence.*;

@Entity // Le dice a Spring (con JPA/Hibernate):
@Table(name = "autores") //creamos la tabla de autores
public class Autor {
    @Id // es la clave primaria para cada autor
    @GeneratedValue(strategy = GenerationType.IDENTITY) //le dice a PostgreSQL que cuando se guarda un autor el asigne el siguiente numero que corresponde

    //cada uno se convierte en una columna en la tabla autores
    private Long id;
    private String nombre;
    private Integer nacimiento;
    private Integer muerte;

    // Constructor vacío requerido por JPA para que funcione correctamente es el que costruye los objetos desde la BD
    public Autor() {}

    // Constructor con parámetros (se usa para crear un objeto de una clase
    public Autor(String nombre, Integer nacimiento, Integer muerte) {
        this.nombre = nombre;
        this.nacimiento = nacimiento;
        this.muerte = muerte;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getNacimiento() { return nacimiento; }
    public void setNacimiento(Integer nacimiento) { this.nacimiento = nacimiento; }

    public Integer getMuerte() { return muerte; }
    public void setMuerte(Integer muerte) { this.muerte = muerte; }

    @Override

    //metodo especial que todad las clasas heredan de la clase objet. es convertir un objeto en una cadena de texto legible
    public String toString() {
        return String.format("%s (Nacido: %s | Muerto: %s)",
                nombre != null ? nombre : "Desconocido",
                nacimiento != null ? nacimiento : "Desconocido",
                muerte != null ? muerte : "Desconocido");
    }
}

