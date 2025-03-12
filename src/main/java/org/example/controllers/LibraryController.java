package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.models.Book;
import org.example.models.Library;
import org.example.services.LibraryService;
import org.example.utils.FileHandler;

public class LibraryController {
    Library library;
    FileHandler fileHandler;
    LibraryService libraryService;

    @FXML private TextField bookTitleField;
    @FXML private TextField bookAuthorField;
    @FXML private TextField bookPublishDateField;
    @FXML private TextField bookTitleFilterField;
    @FXML private TableView<Book> bookTableView;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, String> dateColumn;
    @FXML private Button loanBookButton;


    public void setLibrary(Library library) {
        this.library = library;
        this.fileHandler = new FileHandler(library); // Přidá FileHandler
        this.libraryService = new LibraryService(library);
        fileHandler.loadLibraryFromFile(); // Načte uložená data
        updateBookList(); // Po nastavení knihovny rovnou načteme knihy
    }

    public void updateBookList() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));

        bookTableView.setItems(FXCollections.observableArrayList(library.getBooks()));
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
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            library.removeBook(selectedBook);
            fileHandler.saveLibraryToFile();
            updateBookList();
        }
    }

    @FXML private void findBook() {
        String title = bookTitleFilterField.getText();

        if (title != null && !title.isEmpty() && libraryService.findBookByTitle(title) != null) {
           Book book = libraryService.findBookByTitle(title);
           bookTableView.getItems().clear();
           bookTableView.getItems().add(book);
           bookTitleField.clear();
        } else if (title.isEmpty()) {
            updateBookList();
        } else {
            bookTableView.getItems().clear();
        }
    }

    @FXML private void handleLoanBook() {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            System.out.println(selectedBook);
            System.out.println("Book loaned: " + selectedBook);
            bookTableView.getItems().remove(selectedBook);
        }
    }
}
