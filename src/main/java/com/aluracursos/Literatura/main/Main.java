package com.aluracursos.Literatura.main;

import com.aluracursos.Literatura.model.Authors;
import com.aluracursos.Literatura.model.Books;
import com.aluracursos.Literatura.model.Data;
import com.aluracursos.Literatura.model.DataBooks;
import com.aluracursos.Literatura.repository.BooksRepository;
import com.aluracursos.Literatura.service.Api;
import com.aluracursos.Literatura.service.ConvertData;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private BooksRepository repoBooks;

    private static final String URL_API = "https://gutendex.com/books/";
    private Api connectionApi = new Api();
    private ConvertData convertData = new ConvertData();
    private Scanner input = new Scanner(System.in);
    String menu = """
            --------------------------------------------------
            |  Elija la opcion indicada:                     |\s
            |  1.- Buscar libro por titulo                   |\s
            |  2.- Listar libros registrados                 |\s
            |  3.- Listar autores registrados                |\s
            |  4.- Listar autores vivos en determinado año   |\s
            |  5.- Listar libros idioma                      |\s
            |  0.- Salir                                     |\s
            --------------------------------------------------
           \s""";
    String lang = """
            -------------------------------------------------
            |  Elija la opcion indicada:                    |\s
            |  1.- ES - Español                             |\s
            |  2.- EN - Inglés                              |\s
            |  3.- FR - Francés                             |\s
            |  4.- DU - Alemán                              |\s
            |  5.- AR - Arabic                              |\s
            -------------------------------------------------
           \s""";

    public Main(BooksRepository repoBooks) {
        this.repoBooks = repoBooks;

    }

    public void run() {
        var option = -1;

        while (option != 0) {
            System.out.println(menu);
            option = input.nextInt();
            input.nextLine();
            switch (option) {
                case 1:
                    searchBookByTitle();
                    break;
                case 2:
                    showRegisteredBooks();
                    break;
                case 3:
                    showRegisteredAuthors();
                    break;
                case 4:
                    showAliveAuthorsByYear();
                    break;
                case 5:
                    showBooksByLanguage();
                    break;
                case 0:
                    System.out.println("Saliendo del Programa");
                    break;
                default:
                    System.out.println("Elija una pción valida");
                    break;
            }
        }
    }

    private void showBooksByLanguage() {
        System.out.println(lang);
        var optionLang=input.nextInt();
        String lang="";
        System.out.println("Estos son los libros en: " + "\n");
        switch (optionLang){
            case 1:
                lang="es";
                break;
            case 2:
                lang="en";
                break;
            case 3:
                lang="fr";
                break;
            case 4:
                lang="du";
                break;
            case 5:
                lang="ar";
                break;
            default:
                lang="en";
                break;
        }
        List<Books> books = repoBooks.getLanguages(lang);
        printBooks(books);
    }

    private void showAliveAuthorsByYear() {
        System.out.println("Selecciona período de años");
        System.out.println("Introduce el primer año del rango: ");
        var date1 = input.nextInt();
        input.nextLine();
        System.out.println("Introduce el segundo año del rango: ");
        var date2 = input.nextInt();
        input.nextLine();
        if (date1 < date2) {
            List<Authors> authorsList = repoBooks.getAuthorsBetweenDates(date1, date2);
            if (!authorsList.isEmpty()) {
                System.out.println("Estos son los autores vivos entre el año " + date1 + " y " + date2 + " :");
                System.out.println("               AUTORES                ");
                for (int i = 0; i < authorsList.size(); i++) {
                    System.out.println("--------------------------");
                    System.out.println("Nombre: " + authorsList.get(i).getName());
                    System.out.println("Año de nacimiento: " + authorsList.get(i).getBirth_year());
                    System.out.println("Año de muerte: " + authorsList.get(i).getDeath_year());
                    System.out.println("--------------------------");
                }
            } else {
                System.out.println("No existen autores dentro del rango de la selección \n");
            }

        } else {
            System.out.println("Las fechas no son correctas\n");
        }

    }

    private void showRegisteredAuthors() {
        System.out.println("Estos son los autores registrados en nuestra base de datos local: \n");
        List<Books> books = repoBooks.findAll();
        printAuthors(books);
    }

    private void showRegisteredBooks() {
        System.out.println("Estos son los libros registrados en nuestra base de datos local: \n");
        List<Books> books = repoBooks.findAll();
        printBooks(books);
    }

    private void searchBookByTitle() {
        DataBooks data = getDataBooks();
        Books book = new Books(data);
        List<Authors> authorsList = new ArrayList<>();
        for (int i = 0; i < data.authors().size(); i++) {
            Authors author = new Authors(data.authors().get(i));
            authorsList.add(author);
        }
        book.setAuthorsList(authorsList);
        List<Books> printBook = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            printBook.add(book);
        }
        printBooks(printBook);
        repoBooks.save(book);
    }

    private DataBooks getDataBooks() {
        System.out.println("Escribe el nombre del libro:");
        var bookName = input.nextLine();
        System.out.println("Buscando libro por titulo... \n");
        var json = connectionApi.connectionApi(URL_API + "?search=" + bookName.replace(" ", "+"));

        Data data = convertData.getData(json, Data.class);
        Optional<DataBooks> foundBook = data.books().stream()
                .filter(l -> l.title().toUpperCase().contains(bookName.toUpperCase()))
                .findFirst();
        if (foundBook.isPresent()) {
            System.out.println("Libro Encontrado ");

            return foundBook.get();
        } else {
            System.out.println("Libro no encontrado");
        }
        return null;
    }

    public void printBooks(List<Books> books) {
        if (!books.isEmpty()) {
            for (Books book : books) {
                System.out.println("********* LIBROS **********");
                System.out.println(" Titulo: " + book.getTitle());
                for (Authors authors : book.getAuthorsList()) {
                    System.out.println(" Autor: " + authors.getName());
                    System.out.println(" Nacio en: " + authors.getBirth_year());
                    System.out.println(" Murio en: " + authors.getDeath_year());
                }
                System.out.println(" Idioma: " + book.getLanguages());
                System.out.println(" Descargas: " + book.getDownloads());
                System.out.println("-------------------------");
            }
        } else {
            System.out.println("No se han agregado libros");
        }
    }

    public void printAuthors(List<Books> books) {
        if (!books.isEmpty()) {
            System.out.println("************* AUTORES **************");
            for (Books book : books) {
                for (Authors authors : book.getAuthorsList()) {
                    System.out.println("-------------------------");
                    System.out.println(" Autor: " + authors.getName());
                    System.out.println(" Nacio en: " + authors.getBirth_year());
                    System.out.println(" Murio en: " + authors.getDeath_year());
                    System.out.println("-------------------------");
                }
            }
        } else {
            System.out.println("No se han agregado libros, por lo que no tenemos autores");
        }
        System.out.println("-------------------------");
    }
}
