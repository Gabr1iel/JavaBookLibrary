package org.example.services;

import org.example.models.Book;
import org.example.models.Genre;
import org.example.utils.AlertUtils;
import org.example.utils.BinaryFileHandler;

import java.util.*;
import java.util.stream.Collectors;

public class BookServices {
    private HashMap<String, Book> books;
    private final BinaryFileHandler binaryFileHandler;

    public BookServices(BinaryFileHandler binaryFileHandler) {
        this.binaryFileHandler = binaryFileHandler;
        this.books = binaryFileHandler.loadContent("data/books_data.ser", Book.class);
    }

    public void addBook(Book book) {
        books.put(book.getId(), book);
    }

    public void saveBooks() {
        binaryFileHandler.save("data/books_data.ser", books, Book.class);
    }

    public void removeBook(Book book) {
        if (book.isLoaned()) {
            AlertUtils.showErrorAlert("Error during book removal", "Cant remove book that is loaned!");
            return;
        }
        books.remove(book.getId());
    }

    public void updateBook(Book updatedBook) {
        if (books.containsKey(updatedBook.getId())) {
            saveBooks();
        }
    }

    public void makeBookAvailable(Book book) {
        book.returnBook();
        saveBooks();
    }

    public List<Book> getAvailableBooks() {
        return books.values().stream().filter(book -> !book.isLoaned()).collect(Collectors.toList());
    }

    public Book findBookByTitle(String bookTitle) {
        for (Book book : getBooks().values()) {
            if (book.getTitle().equals(bookTitle)) {
                System.out.println("Knížka: " + book.getTitle());
                return book;
            }
        }
        return null;
    }

    public Map<String, Book> findBookByAuthor(String bookAuthor, HashMap<String, Book> books) {
        return books.values().stream()
                .filter(book -> book.getAuthor().equals(bookAuthor))
                .collect(Collectors.toMap(
                        Book::getId,
                        book -> book
                ));
    }

    public Map<String, Book> findBookByGenre(String bookGenre, HashMap<String, Book> books) {
        return books.values().stream()
                .filter(book -> book.getBookGenres().stream().toList().contains(bookGenre))
                .collect(Collectors.toMap(
                        Book::getId,
                        book -> book
                ));
    }

    public HashMap<String, Book> getBooks() {
        return books;
    }
}
