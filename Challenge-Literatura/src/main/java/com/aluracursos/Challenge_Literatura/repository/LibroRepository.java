package com.aluracursos.Challenge_Literatura.repository;

import com.aluracursos.Challenge_Literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findById(Long id);

    Optional<Libro> findByTitulo(String titulo);

    // 👇 Nuevo método: Cargar todos los libros con sus autores pre-cargados
    @Query("SELECT l FROM Libro l LEFT JOIN FETCH l.autorIds")
    List<Libro> findAllWithAutores();

   /* // 👇 Método opcional: Buscar por título con autores pre-cargados
    @Query("SELECT l FROM Libro l LEFT JOIN FETCH l.autorIds WHERE l.titulo = :titulo")
    List<Libro> findByTituloWithAutores(@Param("titulo") String titulo);*/


}