package org.example;

import org.example.models.Book;
import org.example.models.Library;
import org.example.models.Reader;
import org.example.services.LibraryService;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        LibraryService libraryService = new LibraryService(library);
        libraryService.loadLibraryFromFile();

        Reader reader = new Reader("Emil Debil", "19.4.2001", "U Prdele, Praha");

        // Testování základních operací
        libraryService.loanBook(libraryService.findBookByTitle("Farma Zvířat"), reader);
        libraryService.loanBook(libraryService.findBookByTitle("Farma Zvířat"), reader);
        libraryService.loanBook(libraryService.findBookByTitle("Pán prstenů"), reader);
        reader.getBorrowedBooks().forEach(book -> System.out.println("Knížka byla zapůjčena " + book.getTitle()));
        libraryService.returnBook(libraryService.findBookByTitle("Farma Zvířat"), reader);


        library.getBooks().forEach(book -> System.out.println(book.getTitle()));

        // Filtrování knížek podle názvu/autora
        libraryService.findBookByTitle("Farma Zvířat");
        libraryService.findBookByAuthor("George Orwell");

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

        // Ukládání do souboru
        libraryService.saveLibraryToFile();
        library.setBooks(null);

        libraryService.loadLibraryFromFile();

        System.out.println("Načtené knihy: ");
        for (Book book : library.getBooks()) {
            System.out.println(book.getTitle() + " - " + book.getAuthor());
        }

    }
}