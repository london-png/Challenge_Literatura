# Challenge_Literatura
Un proyecto educativo en Java + Spring que permite explorar, consultar y gestionar libros y autores desde la API pública de Gutendex, almacenando la información en una base de datos PostgreSQL.
Badges:
https://www.oracle.com/java/?spm=a2ty_o01.29997173.0.0.4ab85171mYq9vI, 
https://spring.io/projects/spring-boot?spm=a2ty_o01.29997173.0.0.4ab85171mYq9vI
https://www.postgresql.org/?spm=a2ty_o01.29997173.0.0.4ab85171mYq9vI
https://maven.apache.org/?spm=a2ty_o01.29997173.0.0.4ab85171mYq9vI
****************************************************************************************************************************************************************************************************
Indice
Descripción del Proyecto
Estado del Proyecto
Características de la Aplicación y Demostración
Acceso al Proyecto
Tecnologías Utilizadas
Personas Contribuyentes
Personas Desarrolladoras del Proyecto
****************************************************************************************************************************************************************************************************
Descripción del Proyecto
Este proyecto, llamado Challenge Literatura, es una aplicación de consola desarrollada en Java con Spring Boot, que permite interactuar con la API de Gutendex para obtener información sobre libros y autores del dominio público. La aplicación ofrece un menú interactivo con funcionalidades como:

Búsqueda de libros por título.
Registro automático de la información en una base de datos PostgreSQL.
Listado de libros y autores ya consultados.
Filtrado de autores vivos en un rango de años.
Búsqueda de libros por idioma.
El objetivo es facilitar el acceso a obras literarias clásicas mientras se practican buenas prácticas de programación como el uso de Spring Data JPA, streams, lambda expressions, manejo de errores, validación de entradas y separación de responsabilidades en diferentes capas del software.
****************************************************************************************************************************************************************************************************
Estado del Proyecto
Finalizado y funcional

Menú interactivo con validación robusta de entradas.
Integración completa con PostgreSQL.
Persistencia de libros y autores sin duplicados.
Manejo seguro de errores y excepciones.
Código modular, limpio y bien documentado.
****************************************************************************************************************************************************************************************************
Características de la Aplicación y Demostración
Menú interactivo con 6 opciones:
Buscar libro por título
Si el libro existe en la API, se almacena en la base de datos (libros, autores, libro_autor).
Se muestra: título, autor(es), fechas de nacimiento/muerte, idioma, resumen, ID, descargas y formatos disponibles.
Listar libros registrados
Muestra todos los libros almacenados con: título, autor(es), idioma, ID, número de descargas y fecha de registro.
Listar autores registrados
Lista únicos de autores con: nombre, ID, año de nacimiento y muerte.
Listar autores vivos en un determinado año
El usuario ingresa un rango de años (inicio y fin).
Se filtran los autores cuyo rango de vida incluye dicho período.
Validación estricta: solo acepta números enteros.
Listar libros por idioma
Muestra los idiomas disponibles en la base de datos.
El usuario selecciona un idioma y se listan los libros correspondientes.
Salir
Finaliza la ejecución del programa.
Validación robusta: Si el usuario ingresa letras, símbolos o entradas no válidas en cualquier opción numérica, el sistema muestra un mensaje claro y vuelve a solicitar la entrada.
****************************************************************************************************************************************************************************************************
Acceso al Proyecto
El código fuente está disponible en GitHub:https://github.com/london-png/Challenge_Literatura.git

Repositorio en GitHub (reemplazar con tu enlace real)

Requisitos para ejecutar el proyecto:
Java 17 o superior
Maven
PostgreSQL instalado y corriendo
Base de datos creada: Challenge_Literatura
Configuración de credenciales en application.properties
****************************************************************************************************************************************************************************************************
Tecnologías Utilizadas
Lenguaje: Java 17+
Framework: Spring Boot 3.x
Persistencia: Spring Data JPA + Hibernate
Base de datos: PostgreSQL 15+
Gestión de dependencias: Apache Maven
API externa: Gutendex
Conceptos aplicados:
Programación orientada a objetos (POO)
Records (Java 14+)
Streams y expresiones Lambda
Manejo de excepciones personalizadas
Validación de entradas de usuario
Arquitectura por capas (Controller → Service → Repository)
Inyección de dependencias (@Autowired)
Consultas JPQL personalizadas
Colecciones y manejo de listas
****************************************************************************************************************************************************************************************************
ersonas Contribuyentes
Este proyecto fue desarrollado como parte de un reto educativo 
****************************************************************************************************************************************************************************************************
Personas Desarrolladoras del Proyecto
Carlos Fernando Palacios Gracia
Programador autodidacta y apasionadola tecnología.
Desarrollador principal: diseño de la arquitectura, integración con la API, persistencia en base de datos, menú interactivo y validación de entradas.
cfpalaciwalker@gmail.com

