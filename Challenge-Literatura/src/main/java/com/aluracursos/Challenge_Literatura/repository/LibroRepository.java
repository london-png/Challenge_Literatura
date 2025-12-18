// com.aluracursos.Challenge_Literatura.repository.LibroRepository.java

package com.aluracursos.Challenge_Literatura.repository;

import com.aluracursos.Challenge_Literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

//permite guardar, buscar y consultar libros en la base de datos
public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findById(Long id);
    Optional<Libro> findByTitulo(String titulo);

    //obtener todos los libros y carga a la vez los ids del los autores
    @Query("SELECT l FROM Libro l LEFT JOIN FETCH l.autorIds")
    List<Libro> findAllWithAutores();

    // Obtener todos los idiomas únicos (JPQL funciona aquí)
    @Query("SELECT DISTINCT l.idioma FROM Libro l")
    List<String[]> findAllLanguages();

    //  Obtener libros por idioma (Native Query para PostgreSQL)
    @Query(value = "SELECT DISTINCT * FROM libros l WHERE ?1 = ANY(l.idioma)", nativeQuery = true)
    List<Libro> findByLanguage(@Param("idioma") String idioma);
}