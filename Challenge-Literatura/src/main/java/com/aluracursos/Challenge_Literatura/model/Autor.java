package com.aluracursos.Challenge_Literatura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer nacimiento;
    private Integer muerte;

    // Constructor vacío requerido por JPA
    public Autor() {}

    // Constructor con parámetros
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
    public String toString() {
       /* return "Autor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", nacimiento=" + nacimiento +
                ", muerte=" + muerte +
                '}';*/
        return String.format("%s (Nacido: %s | Muerto: %s)",
                nombre != null ? nombre : "Desconocido",
                nacimiento != null ? nacimiento : "Desconocido",
                muerte != null ? muerte : "Desconocido");
    }
}

