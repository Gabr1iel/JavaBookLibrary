package org.example.book;

import org.example.genre.Genre;
import org.example.ui.alerts.AlertUtils;
import org.example.common.file.BinaryFileHandler;

import java.util.*;
import java.util.stream.Collectors;

public class BookServices {
    private final HashMap<String, Book> books;
    private final BinaryFileHandler binaryFileHandler;

    public BookServices(BinaryFileHandler binaryFileHandler) {
        this.binaryFileHandler = binaryFileHandler;
        this.books = binaryFileHandler.loadContent("data/books_data.ser", Book.class);
    }

    public void saveBooks() {binaryFileHandler.save("data/books_data.ser", books, Book.class);}

    public void createBook(String title, String author, String publishDate, Genre genre) {
        if (!title.trim().isEmpty() && !author.trim().isEmpty() && !publishDate.trim().isEmpty() && genre != null) {
            List<String> genres = new ArrayList<>();
            genres.add(genre.getTitle());
            Book newBook = new Book(title, author, publishDate, genres);
            books.put(newBook.getId(), newBook);
            saveBooks();
        } else
            AlertUtils.showErrorAlert("Missing information!","Please enter all information about book!");
    }

    public void removeBook(Book book) {
        if (book.isLoaned())
            AlertUtils.showErrorAlert("Error during book removal", "Cant remove book that is loaned!");
        books.remove(book.getId());
        saveBooks();
    }

    public void editBook(Book updatedBook) {
        if (books.containsKey(updatedBook.getId())) {
            saveBooks();
        }
    }

    public Book findBookByTitle(String bookTitle) {
        return books.values().stream().filter(b -> b.getTitle().equalsIgnoreCase(bookTitle)).findFirst().orElse(null);
    }

    public Map<String, Book> findBookByAuthor(String bookAuthor, HashMap<String, Book> books) {
        return books.values().stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(bookAuthor))
                .collect(Collectors.toMap(
                        Book::getId,
                        book -> book
                ));
    }

    public Map<String, Book> findBookByGenre(String bookGenre, HashMap<String, Book> books) {
        return books.values().stream()
                .filter(book -> book.getBookGenres().stream().anyMatch(genre -> genre.equalsIgnoreCase(bookGenre)))
                .collect(Collectors.toMap(
                        Book::getId,
                        book -> book
                ));
    }

    public Map<String, Book> filterBooks(String title, String author, String genre) {
        Map<String, Book> filteredBooks = new HashMap<>();
        var bookByTitle = findBookByTitle(title);

        if (bookByTitle != null) {
            filteredBooks.put(bookByTitle.getId(), bookByTitle);
            return filteredBooks;
        }

        filteredBooks.putAll(findBookByAuthor(author, books)); // Najde knížky podle autora / vrátí prázdnou mapu
        if (filteredBooks.isEmpty())
            filteredBooks.putAll(findBookByGenre(genre, books));
        if (!genre.trim().isEmpty())
            filteredBooks.entrySet().removeIf(entry -> !entry.getValue().getBookGenres().contains(genre)); // projde mapu knížek autora a vrátí pouze ty s daným žánrem

        return filteredBooks;
    }

    public List<Book> getAvailableBooks() {
        return books.values().stream().filter(book -> !book.isLoaned()).collect(Collectors.toList());
    }

    public HashMap<String, Book> getBooks() {
        return books;
    }
}
