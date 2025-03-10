package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.models.Book;
import org.example.models.Library;
import org.example.utils.FileHandler;


import java.util.List;

public class LibraryController {
    Library library;
    FileHandler fileHandler;

    @FXML private TextField bookTitleField;
    @FXML private TextField bookAuthorField;
    @FXML private TextField bookPublishDateField;
    @FXML private ListView<String> bookListView;
    @FXML private Button loanBookButton;

    public void setLibrary(Library library) {
        this.library = library;
        this.fileHandler = new FileHandler(library); // Přidá FileHandler
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
        if (!bookTitle.isEmpty() && !bookAuthor.isEmpty() && !bookPublishDate.isEmpty()) {
            Book newBook = new Book(bookTitle, bookAuthor, bookPublishDate);
            library.addBook(newBook);
            fileHandler.saveLibraryToFile();
            updateBookList();
            bookTitleField.clear();
            bookAuthorField.clear();
            bookPublishDateField.clear();
        }
    }

    @FXML private void handleLoanBook() {
        String selectedBook = bookListView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            System.out.println("Book loaned: " + selectedBook);
            bookListView.getItems().remove(selectedBook);
        }
    }



}
