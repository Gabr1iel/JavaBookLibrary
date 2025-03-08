package org.example;

import org.example.models.Book;
import org.example.models.Library;
import org.example.models.Reader;
import org.example.services.LibraryService;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        LibraryService libraryService = new LibraryService(library);

        Book book1 = new Book("1984", "George Orwell", "1949");
        Book book2 = new Book("Farma Zvířat", "George Orwell", "1950");
        Book book3 = new Book("Pán prstenů", "Tolkein", "1952");
        Reader reader = new Reader("Emil Debil", "19.4.2001", "U Prdele, Praha");

        // Testování základních operací
        libraryService.loanBook(book1, reader);
        libraryService.loanBook(book1, reader);
        libraryService.loanBook(book2, reader);
        reader.getBorrowedBooks().forEach(book -> System.out.println(book.getTitle()));
        libraryService.returnBook(book1, reader);

        library.addBook(book2);
        library.addBook(book1);
        library.addBook(book3);
        library.getBooks().forEach(book -> System.out.println(book.getTitle()));

        // Filtrování knížek podle názvu/autora
        libraryService.finBookByTitle("Farma Zvířat");
        libraryService.finBookByAuthor("George Orwell");

        // Výpis knih seřazených podle názvu
        System.out.println("Knihy seřazené podle názvu:");
        for (Book book : libraryService.sortByTitle()) {
            System.out.println(book.getTitle() + " - " + book.getAuthor());
        }

        // Výpis knih seřazených podle autora
        System.out.println("Knihy seřazené podle autora:");
        for (Book book : libraryService.sortByAuthor()) {
            System.out.println(book.getTitle() + " - " + book.getAuthor());
        }
    }
}