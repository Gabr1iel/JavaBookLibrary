package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.models.Book;
import org.example.models.Library;
import org.example.services.LibraryService;
import org.example.utils.FileHandler;


import java.util.List;

public class LibraryController {
    Library library;
    FileHandler fileHandler;
    LibraryService libraryService;

    @FXML private TextField bookTitleField;
    @FXML private TextField bookAuthorField;
    @FXML private TextField bookPublishDateField;
    @FXML private TextField bookTitleFilterField;
    @FXML private ListView<String> bookListView;
    @FXML private Button loanBookButton;

    public void setLibrary(Library library) {
        this.library = library;
        this.fileHandler = new FileHandler(library); // Přidá FileHandler
        this.libraryService = new LibraryService(library);
        fileHandler.loadLibraryFromFile(); // Načte uložená data
        updateBookList(); // Po nastavení knihovny rovnou načteme knihy
    }

    public void updateBookList() {
        bookListView.getItems().clear();
        System.out.println("Načtené knihy: ");
        for (Book book : library.getBooks()) {
            bookListView.getItems().add(book.getTitle());
            System.out.println(book.getTitle() + " - " + book.getAuthor());
        }
    }

    @FXML public void initialize() {
        System.out.println("LibraryController byl inicializován!");
    }

    @FXML private void handleAddBook() {
        String bookTitle = bookTitleField.getText();
        String bookAuthor = bookAuthorField.getText();
        String bookPublishDate = bookPublishDateField.getText();
        if (!bookTitle.trim().isEmpty() && !bookAuthor.trim().isEmpty() && !bookPublishDate.trim().isEmpty()) {
            Book newBook = new Book(bookTitle, bookAuthor, bookPublishDate);
            library.addBook(newBook);
            fileHandler.saveLibraryToFile();
            updateBookList();
            bookTitleField.clear();
            bookAuthorField.clear();
            bookPublishDateField.clear();
        }
    }

    @FXML private void handleRemoveBook() {
        String selectedBook = bookListView.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            Book book = libraryService.findBookByTitle(selectedBook);
            library.removeBook(book);
            fileHandler.saveLibraryToFile();
            updateBookList();
        }
    }

    @FXML private void findBook() {
        String title = bookTitleFilterField.getText();

        if (title != null && !title.isEmpty() && libraryService.findBookByTitle(title) != null) {
           Book book = libraryService.findBookByTitle(title);
           bookListView.getItems().clear();
           bookListView.getItems().add(book.getTitle());
           bookTitleField.clear();
        } else if (title.isEmpty()) {
            updateBookList();
        } else {
            bookListView.getItems().clear();
            bookListView.getItems().add("Knížka není v databázi!");
        }
    }

    @FXML private void handleLoanBook() {
        String selectedBook = bookListView.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            System.out.println(selectedBook);
            System.out.println("Book loaned: " + selectedBook);
            bookListView.getItems().remove(selectedBook);
        }
    }
}
