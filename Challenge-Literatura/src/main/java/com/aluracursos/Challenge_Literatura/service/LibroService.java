package com.aluracursos.Challenge_Literatura.service;

import com.aluracursos.Challenge_Literatura.model.*;
import com.aluracursos.Challenge_Literatura.repository.AutorRepository;
import com.aluracursos.Challenge_Literatura.repository.LibroRepository;
import com.aluracursos.Challenge_Literatura.util.FormateadorDeTexto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LibroService {

    private final ConsumoApi consumoApi;
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final ObjectMapper objectMapper;

    // 👇 Eliminado: Ya no necesitamos EntityManager

    @Autowired
    public LibroService(
            ConsumoApi consumoApi,
            LibroRepository libroRepository,
            AutorRepository autorRepository,
            ObjectMapper objectMapper) {
        this.consumoApi = consumoApi;
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public void buscarYMostrarLibros(String titulo) {
        String tituloNormalizado = titulo.trim();
        String urlEncoded = URLEncoder.encode(tituloNormalizado, StandardCharsets.UTF_8);
        // 👇 Corregido: Eliminado el espacio extra al final de la URL
        String URL_BASE = "https://gutendex.com/books/?search=" + urlEncoded; // ✅ Sin espacio

        try {
            RespuestaApi respuesta = consumoApi.obtenerDatos(URL_BASE);
            if (respuesta.resultados() == null || respuesta.resultados().length == 0) {
                System.out.println("No se encontraron libros con ese titulo");
                return;
            }

            Map<String, List<DatosLibros>> librosPorTitulo = new HashMap<>();
            for (DatosLibros libro : respuesta.resultados()) {
                String t = libro.Titulo();
                librosPorTitulo.computeIfAbsent(t, k -> new ArrayList<>()).add(libro);
            }

            System.out.println("\n Resultados encontrados");
            for (Map.Entry<String, List<DatosLibros>> entrada : librosPorTitulo.entrySet()) {
                String tituloReal = entrada.getKey();
                List<DatosLibros> versiones = entrada.getValue();

                System.out.println("\n Titulo: " + tituloReal);
                System.out.println(" Versiones disponibles (" + versiones.size() + "):");

                for (int i = 0; i < versiones.size(); i++) {
                    DatosLibros datosLibros = versiones.get(i);

                    // Convertir y guardar el libro
                    Libro libroEntidad = convertirYGuardarLibro(datosLibros);

                    // 👇 Obtener autores reales usando los IDs almacenados
                    List<Autor> autoresReales = obtenerAutoresPorIds(libroEntidad.getAutorIds());

                    String autores = "Desconocido";
                    if (!autoresReales.isEmpty()) {
                        autores = autoresReales.stream()
                                .map(autor -> String.format("%s (Nacimiento: %s | Muerte: %s)",
                                        autor.getNombre(),
                                        autor.getNacimiento() != null ? autor.getNacimiento() : "Desconocido",
                                        autor.getMuerte() != null ? autor.getMuerte() : "Desconocido"))
                                .collect(Collectors.joining(", "));
                    }

                    String idiomas = (datosLibros.Idioma() != null && datosLibros.Idioma().length > 0)
                            ? String.join(", ", datosLibros.Idioma()) : "Desconocido";

                    Integer descargas = datosLibros.Descargas() != null ? datosLibros.Descargas() : 0;

                    String resumen = "No tiene resumen";
                    if (datosLibros.Resumen() != null && datosLibros.Resumen().length > 0) {
                        resumen = FormateadorDeTexto.formatear(datosLibros.Resumen()[0], 80);
                    }

                    System.out.printf("    %d. ID: %d\n", i + 1, datosLibros.Id());
                    System.out.printf("       Autores: %s\n", autores);
                    System.out.printf("       Idioma(s): %s\n", idiomas);
                    System.out.printf("       Descargas: %d\n", descargas);
                    System.out.println("       Resumen:");
                    System.out.println("      " + resumen);
                    System.out.println("       ----------------------------------------");
                }
            }
            System.out.println("\n Libros consultados y guardados en la base de datos");
        } catch (Exception e) {
            System.err.println("Error en el proceso de busquedad: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Transactional
    private Libro convertirYGuardarLibro(DatosLibros datos) throws Exception {
        Optional<Libro> libroExistente = libroRepository.findById(datos.Id());
        if (libroExistente.isPresent()) {
            return libroExistente.get(); // ✅ Ahora no hay problema con relaciones
        }

        // 👇 Convertir autores y obtener sus IDs
        List<Long> autorIds = new ArrayList<>();
        if (datos.Autores() != null) {
            for (DatosAutor da : datos.Autores()) {
                Autor autor = autorRepository.findByNombre(da.Nombre())
                        .orElseGet(() -> {
                            Autor nuevoAutor = new Autor(da.Nombre(), da.Nacimiento(), da.Muerte());
                            return autorRepository.save(nuevoAutor);
                        });
                autorIds.add(autor.getId());
            }
        }

        String formatosJson = objectMapper.writeValueAsString(datos.formatos()); // ✅ Corregido

        Libro libro = new Libro();
        libro.setId(datos.Id());
        libro.setTitulo(datos.Titulo());
        libro.setIdioma(datos.Idioma());
        libro.setAutorIds(autorIds); // 👈 Usamos IDs en lugar de objetos
        libro.setDescargas(datos.Descargas());
        libro.setResumen(datos.Resumen());
        libro.setFormatos(formatosJson);

        return libroRepository.save(libro);
    }

    // metodo optener los IDs
    public List<Autor> obtenerAutoresPorIds(List<Long> autorIds) {
        if (autorIds == null || autorIds.isEmpty()) {
            return Collections.emptyList();
        }
        return autorRepository.findAllById(autorIds);
    }
    //metodo para listar los libros registrados
    public List<Libro> listarTodosLosLibros() {
        return libroRepository.findAllWithAutores();
    }
    //metodo para que realice el conteo de libros registrados en la base de datos
    public long contadorLibrosRegistrados() {
        return libroRepository.count(); //Usa el metodo incorporado de jpaReository
    }
}