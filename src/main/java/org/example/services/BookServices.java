package org.example.services;

import org.example.models.Book;
import org.example.models.Library;
import org.example.models.Reader;
import org.example.utils.FileHandler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookServices {
    private List<Book> books;
    private FileHandler fileHandler;

    public BookServices(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        this.books = fileHandler.loadBooksFromFile();
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

    public void updateBook(Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(updatedBook.getId())) {
                fileHandler.saveBooksToFile(books);
            }
        }
    }

    public void makeBookAvilable(Book book) {
        book.returnBook();
        fileHandler.saveBooksToFile(books);
    }

    public List<Book> getAvilableBooks() {
        return books.stream().filter(book -> !book.isLoaned()).collect(Collectors.toList());
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
