// com.aluracursos.Challenge_Literatura.service.AutorService.java

package com.aluracursos.Challenge_Literatura.service;

import com.aluracursos.Challenge_Literatura.model.Autor;
import com.aluracursos.Challenge_Literatura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    @Autowired
    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> listarTodosLosAutores() {
        return autorRepository.findAll();
    }

    public long contadorAutoresRegistrados() {
        return autorRepository.count();
    }

    // ✅ Nuevo método: Buscar autores vivos en un rango
    public List<Autor> buscarAutoresVivosEnRango(int inicio, int fin) {
        return autorRepository.findAll().stream()
                .filter(autor -> {
                    Integer nac = autor.getNacimiento();
                    Integer mue = autor.getMuerte();
                    if (nac == null) return false;
                    int muerteReal = (mue != null) ? mue : Integer.MAX_VALUE;
                    return nac <= fin && muerteReal >= inicio;
                })
                .collect(Collectors.toList());
    }
}