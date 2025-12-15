package com.aluracursos.Challenge_Literatura.repository;

import com.aluracursos.Challenge_Literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

//se deben de manejar entidades JPA y repositorios. para que guarde en la base de datos
public interface AutorRepository  extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombre(String nombre);
}
