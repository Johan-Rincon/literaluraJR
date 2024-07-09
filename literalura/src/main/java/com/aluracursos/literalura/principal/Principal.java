package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.*;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.IdiomaRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvertirDatos;

import java.util.*;

public class Principal {
    private Scanner input = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvertirDatos conversorDatos = new ConvertirDatos();
    private AutorRepository repositorioAutor;
    private IdiomaRepository repositorioIdioma;
    private LibroRepository repositorioLibro;

    private Optional<Libro> libroBuscado;

    private final String URL_BASE = "https://gutendex.com/books/?search=";

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository,
                     IdiomaRepository idiomaRepository) {
        this.repositorioLibro = libroRepository;
        this.repositorioAutor = autorRepository;
        this.repositorioIdioma = idiomaRepository;
    }

    public void menu() {
        int opcion = -1;
        while (opcion != 0) {
            var menu = """
                    Elija la opción a través de un número:
                                        
                    1. Buscar libro por título.
                    2. Listar libros registrados.
                    3. Listar autores registrados.
                    4. Listar autores vivos en un determinado año.
                    5. Listar libros por idiomas.
                                        
                    0. Salir
                    """;

            System.out.println(menu);
            opcion = input.nextInt();
            input.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    mostrtarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    mostrarAutoresVivosPorAno();
                    break;
                case 5:
                    mostrarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }


    private void buscarLibroPorTitulo() {
        System.out.println("Ingrese el nombre del libro que desea buscar: ");
        String nombreLibro = input.nextLine();
        Optional<String> resultadoBusqueda = consumoAPI.buscarDatos(nombreLibro);
        //&& conversorDatos.obtenerDatos(resultadoBusqueda.get(), DatosAPI.class).resultados().size() > 0) {
        if (resultadoBusqueda.isPresent()) {


            DatosAPI consultaDB = conversorDatos.obtenerDatos(resultadoBusqueda.get(), DatosAPI.class);
            DatosLibro consultaLibro = consultaDB.resultados().get(0);
            Libro libro = new Libro(consultaLibro);
            Idioma idioma = libro.getIdioma();

            Autor consultaAutor = new Autor(consultaLibro.autor().isEmpty() ?
                    new DatosAutor("N/A", null, null) :
                    consultaLibro.autor().get(0));

            List<Libro> libroEnDB = repositorioLibro.findByTituloContainsIgnoreCase(consultaLibro.titulo());
            List<Autor> autorEnDB = repositorioAutor.findIdByNombreContainsIgnoreCase(consultaAutor.getNombre());
            List<Idioma> idiomaEnDB = repositorioIdioma.findByAbvIdioma(idioma.getAbvIdioma());

            if (!idiomaEnDB.isEmpty()) {
                idiomaEnDB.stream()
                        .forEach(i -> {
                                    if (idioma.getAbvIdioma().contains(i.getAbvIdioma())) {
                                        libro.setIdioma(i);
                                    } else {
                                        repositorioIdioma.save(idioma);
                                    };
                                }
                        );
            } else {
                repositorioIdioma.save(idioma);
            }

            if (libroEnDB.isEmpty() && autorEnDB.isEmpty()) {
                repositorioAutor.save(consultaAutor);
                libro.setAutor(consultaAutor);
                repositorioLibro.save(libro);
            } else if (libroEnDB.isEmpty() && !autorEnDB.isEmpty()) {
                libro.setAutor(autorEnDB.get(0));
                repositorioLibro.save(libro);
            }
            System.out.println(libroEnDB);
        }
        else {
            System.out.println("Libro no encontrado.");
        }
    }

    private void mostrtarLibrosRegistrados() {
        List<Libro> librosEnDb = repositorioLibro.findAll();
        if (librosEnDb.size() > 0) {
            librosEnDb.stream()
                    .forEach(l -> System.out.println(l.toString()));
        }
        else {
            System.out.println("Libro no encontrado.");
        }
    }

    private void mostrarAutoresRegistrados() {
        List<Autor> autores = repositorioAutor.findAll();
        if (autores.size() > 0) {
            autores.stream()
                    .forEach(a -> System.out.println(a.toString()));
        }
        else {
            System.out.println("No hay autores agregados.");
        }
    }

    private void mostrarAutoresVivosPorAno() {
        System.out.println("Ingrese el año para buscar autores vivos: ");
        var anoBusqueda = input.nextInt();

       List<Autor> autores = repositorioAutor.findByAnoMuerteGreaterThan(anoBusqueda);
        if (autores.size() > 0) {
            autores.stream()
                    .forEach(a -> System.out.println(a.toString()));
        }
        else {
            System.out.println("No existen autores vivos para ese año buscado.");
        }

    }

    private void mostrarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma para buscar libros: \n" +
                                "ES - Español\n" +
                                "EN - Inglés\n" +
                                "FR - Francés\n" +
                                "PT - Portugués\n");
        var idiomaBusqueda = input.nextLine();

        //List<Idioma> librosPorIdioma = repositorioIdioma.findByAbvIdioma(idiomaBusqueda);
        List<Idioma> librosPorIdioma = repositorioIdioma.findAll();

        librosPorIdioma.stream()
                .forEach(l -> System.out.println(l.toString()));
    }

}

