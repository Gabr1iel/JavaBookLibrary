package org.example;

import org.example.models.Book;
import org.example.models.Reader;
import org.example.services.LibraryService;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        LibraryService libraryService = new LibraryService();

        Book book1 = new Book("1984", "George Orwell", "1949");
        Book book2 = new Book("Farma Zvířat", "George Orwell", "1950");
        Reader reader = new Reader("Emil Debil", "19.4.2001", "U Prdele, Praha");

        libraryService.loanBook(book1, reader);
        libraryService.loanBook(book1, reader);
        libraryService.loanBook(book2, reader);
        reader.getBorrowedBooks().forEach(System.out::println);
        libraryService.returnBook(book1, reader);
    }
}