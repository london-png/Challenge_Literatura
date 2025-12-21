// com.aluracursos.Challenge_Literatura.service.AutorService.java

package com.aluracursos.Challenge_Literatura.service;

import com.aluracursos.Challenge_Literatura.model.Autor;
import com.aluracursos.Challenge_Literatura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

// es la capa logiga con respecto a lo relacionados con autores no habla directo con la BD esto lo hace AutoRepository
// pero es que filtra y organiza

@Service // indica que es un servicio, guarda y permite inyectar a otras partes como a la clase principal
public class AutorService {

    private final AutorRepository autorRepository;

    // inyecta automaticamente en el repositorio y se usa AutorRepository para hablar con la BD
    @Autowired
    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    //pide al repositorio todos los autores que se encuentran en la BD esto es para la opcion 3 del menu
    public List<Autor> listarTodosLosAutores() {
        return autorRepository.findAll();
    }

    // solicita a la BD cuantos autores hay  registrados en total
    public long contadorAutoresRegistrados() {
        return autorRepository.count();
    }

    // metodo de buscar autores vivos en cierto rango,convierte la lista en un flujo stream y filtra autores que cumplen la regla
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