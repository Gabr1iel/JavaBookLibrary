package org.example.services;

import org.example.models.Book;
import org.example.models.Reader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookServices {
    private List<Book> books;

    public BookServices() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Book> getAvilableBooks() {
        return books.stream().filter(book -> !book.isLoaned()).collect(Collectors.toList());
    }

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
        if (book.isLoaned() && reader.getBorrowedBooks().contains(book)) {
            book.setLoaned(false);
            reader.removeBook(book);
            System.out.println(reader.getName() + " vrátil knihu " + book.getTitle());
        } else {
            System.out.println("Čtenář neměl danou knihu zapůjčenou!");
        }
    }

    public Book findBookByTitle(String bookTitle) {
        for (Book book : getBooks()) {
            if (book.getTitle().equals(bookTitle)) {
                System.out.println("Knížka: " + book.getTitle());
                return book;
            }
        }
        return null;
    }

    public List<Book> findBookByAuthor(String bookAuthor) {
        return getBooks().stream()
                .filter(book -> book.getAuthor().equals(bookAuthor)).toList();
    }

    public List<Book> sortByTitle() {
        return getBooks().stream().sorted(Comparator.comparing(Book::getTitle)).toList();
    }

    public List<Book> sortByAuthor() {
        return getBooks().stream().sorted(Comparator.comparing(Book::getAuthor)).toList();
    }
}
