// com.aluracursos.Challenge_Literatura.service.LibroService.java

package com.aluracursos.Challenge_Literatura.service;

import com.aluracursos.Challenge_Literatura.model.*;
import com.aluracursos.Challenge_Literatura.repository.AutorRepository;
import com.aluracursos.Challenge_Literatura.repository.LibroRepository;
import com.aluracursos.Challenge_Literatura.util.FormateadorDeTexto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aluracursos.Challenge_Literatura.model.DatosAutor;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service

//es la parte de la logica de los libros
public class LibroService {
    //inyeccion de dependencias
    private final ConsumoApi consumoApi;
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final ObjectMapper objectMapper;
    private final ConvierteDatos convierteDatos;

    @Autowired

    //constructor para inicializar  los objetos que esta clase necesita para funciionar
    public LibroService(
            ConsumoApi consumoApi,
            LibroRepository libroRepository,
            AutorRepository autorRepository,
            ObjectMapper objectMapper,
            ConvierteDatos convierteDatos) { // ← Inyectado
        this.consumoApi = consumoApi;
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.objectMapper = objectMapper;
        this.convierteDatos = convierteDatos; // ← Inicializado
    }

    //metodo para tratar una operacion unica con la base de datos guarda libros y autores en la BD
    @Transactional
    public void buscarYMostrarLibros(String titulo) {
        String tituloNormalizado = titulo.trim(); // quit los espacios a inicio o al final del titulo que escibio el usuario
        if (tituloNormalizado.isEmpty()) {
            System.out.println("El título no puede estar vacío.");
            return;
        }

        String urlEncoded = URLEncoder.encode(tituloNormalizado, StandardCharsets.UTF_8);// urlEncoded codifica el titulo para usarlo en una URL
        String URL_BASE = "https://gutendex.com/books/?search=" + urlEncoded;

        try {
            // Consume y  devuelve  un String
            String json = consumoApi.obtenerDatos(URL_BASE);

            //convierte un texto json que viene de internet en un objeto java bien organizado
            RespuestaApi respuesta = convierteDatos.obtenerDatos(json, RespuestaApi.class);

            if (respuesta.resultados() == null || respuesta.resultados().length == 0) {
                System.out.println("No se encontraron libros con ese título.");
                return;
            }

            //crea  un organizador que agrupa por libros
            Map<String, List<DatosLibros>> librosPorTitulo = new HashMap<>();

            //recorre todos los libros y los agrupa por titulo en el map que se creo anteriormente
            for (DatosLibros libro : respuesta.resultados()) {
                String t = libro.Titulo();
                if (t != null) {
                    librosPorTitulo.computeIfAbsent(t, k -> new ArrayList<>()).add(libro);
                }
            }

            //Muestra al usuario una lista organizada de libros, donde cada título aparece una sola vez, acompañado del número de versiones disponibles.
            System.out.println("\nResultados encontrados:");
            for (Map.Entry<String, List<DatosLibros>> entrada : librosPorTitulo.entrySet()) {
                String tituloReal = entrada.getKey();
                List<DatosLibros> versiones = entrada.getValue();

                //muestra cuantas versiona hay de ese libro
                System.out.println("\n🔹 Título: " + tituloReal);
                System.out.println("   Versiones disponibles (" + versiones.size() + "):");

                //Convierte y guarda el libro en la base de datos si no está, Obtiene los autores reales desde la base de datos.
                for (int i = 0; i < versiones.size(); i++) {
                    DatosLibros datosLibros = versiones.get(i);
                    Libro libroEntidad = convertirYGuardarLibro(datosLibros);
                    List<Autor> autoresReales = obtenerAutoresPorIds(libroEntidad.getAutorIds());

                    String autores = "Desconocido";
                    if (!autoresReales.isEmpty()) {
                        autores = autoresReales.stream()

                                //Convierte un objeto Autor en un texto bonito y legible,
                                .map(autor -> String.format("%s (Nac: %s | Mue: %s)",
                                        autor.getNombre(),
                                        autor.getNacimiento() != null ? autor.getNacimiento() : "Desconocido",
                                        autor.getMuerte() != null ? autor.getMuerte() : "Desconocido"))
                                .collect(Collectors.joining(", "));
                    }

                    //convierte un arreglo de códigos de idioma en un texto legible
                    String idiomas = (datosLibros.Idioma() != null && datosLibros.Idioma().length > 0)
                            ? String.join(", ", datosLibros.Idioma()) : "Desconocido";

                    Integer descargas = datosLibros.Descargas() != null ? datosLibros.Descargas() : 0;

                    //es la validacion, si el libro tiene resumen o no, si lo tiene lo ajusta a 80 caracteres
                    String resumen = "No tiene resumen";
                    if (datosLibros.Resumen() != null && datosLibros.Resumen().length > 0) {
                        resumen = FormateadorDeTexto.formatear(datosLibros.Resumen()[0], 80);
                    }

                    //Muestra en la consola, de forma bonita y organizada, toda la información de una versión específica de un libro.
                    System.out.printf("    %d. ID: %d%n", i + 1, datosLibros.Id());
                    System.out.printf("       Autores: %s%n", autores);
                    System.out.printf("       Idioma(s): %s%n", idiomas);
                    System.out.printf("       Descargas: %d%n", descargas);
                    System.out.println("       Resumen:");
                    System.out.println("         " + resumen);
                    System.out.println("       " + "-".repeat(50));
                }
            }
            System.out.println("\nLibros consultados y guardados en la base de datos.");

        } catch (Exception e) {
            System.err.println("❌ Error en la búsqueda: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Transactional
    //Verifica que los datos del libro que viene de la API sean válidos antes de intentar guardarlo.
    private Libro convertirYGuardarLibro(DatosLibros datos) throws Exception {
        if (datos == null || datos.Id() == null) {
            throw new IllegalArgumentException("Datos del libro inválidos");
        }

        //Verifica si el libro ya existe en tu base de datos. Si ya está guardado, lo devuelve inmediatamente y no lo vuelve a guardar.
        Optional<Libro> libroExistente = libroRepository.findById(datos.Id());
        if (libroExistente.isPresent()) {
            return libroExistente.get();
        }

        List<Long> autorIds = new ArrayList<>();
        if (datos.Autores() != null) {
            for (DatosAutor da : datos.Autores()) {
                if (da.Nombre() != null) {
                    Autor autor = autorRepository.findByNombre(da.Nombre())
                            .orElseGet(() -> {
                                Autor nuevoAutor = new Autor(da.Nombre(), da.Nacimiento(), da.Muerte());
                                return autorRepository.save(nuevoAutor);
                            });
                    autorIds.add(autor.getId());
                }
            }
        }

        // cnvierte los datos que vienen de la Api en un objeto libro para guarlarlo en su BD
        String formatosJson = objectMapper.writeValueAsString(datos.formatos());

        Libro libro = new Libro();
        libro.setId(datos.Id());
        libro.setTitulo(datos.Titulo());
        libro.setIdioma(datos.Idioma());
        libro.setAutorIds(autorIds);
        libro.setDescargas(datos.Descargas());
        libro.setResumen(datos.Resumen());
        libro.setFormatos(formatosJson);
        libro.setFechaConsulta(LocalDateTime.now()); //registra la hora exacta que se consulto el libro

        return libroRepository.save(libro);
    }

    //Convierte una lista de IDs de autores en una lista de objetos Autor completos.
    public List<Autor> obtenerAutoresPorIds(List<Long> autorIds) {
        if (autorIds == null || autorIds.isEmpty()) {
            return Collections.emptyList();
        }
        return autorRepository.findAllById(autorIds);
    }

    //Trae todos los libros de la base de datos, junto con sus autores, en una sola operación rápida.
    public List<Libro> listarTodosLosLibros() {
        return libroRepository.findAllWithAutores();
    }

    //realiza el conteo de los libros consultados
    public long contadorLibrosRegistrados() {
        return libroRepository.count();
    }

    //optiene todos los idioas de los libros registrados en la BD si duplicidad
    public List<String> obtenerTodosLosIdiomas() {
        List<String[]> idiomasArray = libroRepository.findAllLanguages();
        return idiomasArray.stream()
                .flatMap(array -> java.util.Arrays.stream(array))
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    //Busca libros en la base de datos por un idioma dado y elimina duplicados por título,
    public List<Libro> obtenerLibrosPorIdioma(String idioma) {
        List<Libro> libros = libroRepository.findByLanguage(idioma);

        // Usamos un Set para rastrear títulos ya vistos (normalizados)
        Set<String> titulosVistos = new HashSet<>();
        return libros.stream()
                .filter(libro -> {
                    if (libro.getTitulo() == null) return true; // o false, según prefieras
                    String tituloNormalizado = libro.getTitulo()
                            .toLowerCase()
                            .replaceAll("[\\-\\s]", "") // Elimina guiones y espacios
                            .trim();
                    return titulosVistos.add(tituloNormalizado); // true si es nuevo
                })
                .collect(Collectors.toList());
         }



}