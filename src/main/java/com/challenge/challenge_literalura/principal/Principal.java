package com.challenge.challenge_literalura.principal;

import com.challenge.challenge_literalura.model.*;
import com.challenge.challenge_literalura.repository.AutorRepository;
import com.challenge.challenge_literalura.repository.LibroRepository;
import com.challenge.challenge_literalura.service.ConsumoAPI;
import com.challenge.challenge_literalura.service.ConvierteDatos;

import java.util.Optional;
import java.util.Scanner;
import java.util.ArrayList;

public class Principal {
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private final String URL_BASE = "http://gutendex.com/books/";
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    -------------------------------------
                    Elija la opción a través de un número:
                    
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                   
                    0 - Salir
                    
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroAPIWeb();
                    break;
//                case 2:
//                    mostrarLibrosBuscados();
//                    break;
//                case 3:
//                    mostrarAutoresBuscados();
//                    break;
//                case 4:
//                    mostrarAutoresBuscadosVivos();
//                    break;
//                case 5:
//                    mostrarLibrosBuscadosPorIdioma();
//                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private DatosLibro getDatosLibro() {
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "%20"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toLowerCase().contains(nombreLibro.toLowerCase()))
                .findFirst();
        if(libroBuscado.isPresent()){
            return libroBuscado.get();
        } else {
            return null;
        }
    }

    private void buscarLibroAPIWeb() {
        DatosLibro datos = getDatosLibro();
        if (datos == null) {
            System.out.println("Ningún libro encontrado.");
            return;
        }
        Autor autor = autorRepository.findByNombreIgnoreCase(datos.autor().getFirst().nombre())
                    .orElseGet(() -> {
                        Autor nuevoAutor = new Autor(datos);
                        autorRepository.save(nuevoAutor);
                        return nuevoAutor;
                    });

        Libro libroExistente = libroRepository.findByTituloIgnoreCase(datos.titulo());
        if(libroExistente != null){
            System.out.println("Libro ya agregado antes");
            return;
        }

        Libro libroNuevo = new Libro(datos);
        libroNuevo.setAutor(autor);

        if (autor.getLibros() == null) {
            autor.setLibros(new ArrayList<>());
        }

        autor.getLibros().add(libroNuevo);

        libroRepository.save(libroNuevo);
        System.out.println(libroNuevo);

    }
}
