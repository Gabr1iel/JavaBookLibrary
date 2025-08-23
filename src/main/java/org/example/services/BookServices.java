package org.example.services;

import org.example.models.Book;
import org.example.models.Genre;
import org.example.utils.AlertUtils;
import org.example.utils.BinaryFileHandler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookServices {
    private List<Book> books;
    private final BinaryFileHandler binaryFileHandler;

    public BookServices(BinaryFileHandler binaryFileHandler) {
        this.binaryFileHandler = binaryFileHandler;
        this.books = binaryFileHandler.loadContent("data/books_data.ser", Book.class);
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void saveBooks() {
        binaryFileHandler.save("data/books_data.ser", books, Book.class);
    }

    public void removeBook(Book book) {
        if (book.isLoaned()) {
            AlertUtils.showErrorAlert("Error during book removal", "Cant remove book that is loaned!");
            return;
        }
        books.remove(book);
    }

    public void updateBook(Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(updatedBook.getId())) {
                binaryFileHandler.save("data/books_data.ser", books, Book.class);
            }
        }
    }

    public void makeBookAvilable(Book book) {
        book.returnBook();
        binaryFileHandler.save("data/books_data.ser", books, Book.class);
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

    public List<Book> findBookByAuthor(String bookAuthor, List<Book> books) {
        return books.stream()
                .filter(book -> book.getAuthor().equals(bookAuthor)).toList();
    }

    public List<Book> findBookByGenre(String bookGenre, List<Book> books) {
        System.out.println(books);
        List<Book> filteredBooks = new ArrayList<>();
        for (Book book : books) {
            List<Genre> genres = book.getBookGenres();
            for (Genre genre : genres) {
                System.out.println(genre.getTitle());
                if (genre.getTitle().equals(bookGenre)) {
                    filteredBooks.add(book);
                }
            }
        }
        System.out.println(filteredBooks);
        return filteredBooks;
    }

    public List<Book> sortByTitle() {
        return getBooks().stream().sorted(Comparator.comparing(Book::getTitle)).toList();
    }

    public List<Book> sortByAuthor() {
        return getBooks().stream().sorted(Comparator.comparing(Book::getAuthor)).toList();
    }

    public List<Book> getBooks() {
        return books;
    }
}
