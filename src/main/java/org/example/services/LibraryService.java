package org.example.services;

import org.example.models.Book;
import org.example.models.Reader;

public class LibraryService {
    public void loanBook(Book book, Reader reader) {
        if (!book.isLoaned()) {
            book.setLoaned(true);
            reader.addBook(book);
            System.out.println(reader.getName() + " si zapůjčil " + book.getTitle());
        } else {
            System.out.println("Kniha momentálně není dostupná");
        }
    }

    public void returnBook(Book book, Reader reader) {
        if (book.isLoaned()) {
            book.setLoaned(false);
            reader.removeBook(book);
            System.out.println(reader.getName() + " vrátil knihu " + book.getTitle());
        } else {
            System.out.println("Čtenář neměl danou knihu zapůjčenou!");
        }
    }
}
