package com.aluracursos.Challenge_Literatura.repository;

import com.aluracursos.Challenge_Literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

//se deben de manejar entidades JPA y repositorios. para que guarde en la base de datos
public interface AutorRepository  extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombre(String nombre);

    //consulta para  Autores vivos en un rango de años
    @Query("SELECT a FROM Autor a WHERE a.nacimiento <= :fin AND (a.muerte >= :inicio OR a.muerte IS NULL)")
    List<Autor> findAutoresVivosEnRango(@Param("inicio") Integer inicio, @Param("fin") Integer fin);
}

