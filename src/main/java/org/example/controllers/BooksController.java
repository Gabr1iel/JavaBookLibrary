package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.models.Book;
import org.example.models.Library;
import org.example.services.BookServices;
import org.example.utils.FileHandler;

public class BooksController {
    Library library;
    FileHandler fileHandler;
    BookServices bookServices;

    @FXML private TextField bookTitleField;
    @FXML private TextField bookAuthorField;
    @FXML private TextField bookPublishDateField;
    @FXML private TextField bookTitleFilterField;
    @FXML private TableView<Book> bookTableView;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, String> dateColumn;
    @FXML private TableColumn<Book, String> loanedColumn;


    public void setBooksFromLibrary(Library library) {
        this.library = library;
        this.fileHandler = new FileHandler(library); // Přidá FileHandler
        this.bookServices = library.getBookServices();
        fileHandler.loadBooksFromFile(); // Načte uložená data
        updateBookList(); // Po nastavení knihovny rovnou načteme knihy
    }

    public void updateBookList() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        loanedColumn.setCellFactory(cellData -> new TableCell<Book, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if(empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                } else {
                    Book book = getTableRow().getItem();
                    if (book.isLoaned()) {
                        setText("Loaned");
                    } else {
                        setText("Avilable");
                    }
                }
            }
        });

        bookTableView.setItems(FXCollections.observableArrayList(library.getBookServices().getBooks()));
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
            library.getBookServices().addBook(newBook);
            fileHandler.saveBooksToFile();
            updateBookList();
            bookTitleField.clear();
            bookAuthorField.clear();
            bookPublishDateField.clear();
        }
    }

    @FXML private void handleRemoveBook() {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            library.getBookServices().removeBook(selectedBook);
            fileHandler.saveBooksToFile();
            updateBookList();
        }
    }

    @FXML private void findBook() {
        String title = bookTitleFilterField.getText();

        if (title != null && !title.isEmpty() && bookServices.findBookByTitle(title) != null) {
           Book book = bookServices.findBookByTitle(title);
           bookTableView.getItems().clear();
           bookTableView.getItems().add(book);
           System.out.println("Book loan status " + book.isLoaned());
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
