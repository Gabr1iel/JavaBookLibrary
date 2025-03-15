package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
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
        this.bookServices = library.getBookServices();
        this.fileHandler = library.getFileHandler();
        //fileHandler.loadBooksFromFile(); // Načte uložená data
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
            fileHandler.saveBooksToFile(library.getBookServices().getBooks());
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
            fileHandler.saveBooksToFile(library.getBookServices().getBooks());
            updateBookList();
        }
    }

    @FXML private void findBook() {
        String title = bookTitleFilterField.getText();

        if (title != null && !title.isEmpty() && bookServices.findBookByTitle(title) != null) {
           Book book = bookServices.findBookByTitle(title);
           bookTableView.getItems().clear();
           bookTableView.getItems().add(book);
           bookTitleField.clear();
        } else if (title.isEmpty()) {
            updateBookList();
        } else {
            bookTableView.getItems().clear();
        }
    }

    @FXML private void handleUpdateBook() {
        Book updatedBook = bookTableView.getSelectionModel().getSelectedItem();
        if (updatedBook != null) {
            Dialog<Book> dialog = new Dialog<>();
            dialog.setTitle("Update Book");
            dialog.setHeaderText("Change book information");

            ButtonType saveBtn = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(saveBtn, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField titleField = new TextField(updatedBook.getTitle());
            TextField authorField = new TextField(updatedBook.getAuthor());
            TextField publishDateField = new TextField(updatedBook.getReleaseDate());

            grid.add(new Label("Title"), 0, 0);
            grid.add(titleField, 1, 0);
            grid.add(new Label("Author"), 0, 1);
            grid.add(authorField, 1, 1);
            grid.add(new Label("Publish Date"), 0, 2);
            grid.add(publishDateField, 1, 2);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveBtn) {
                    updatedBook.setTitle(titleField.getText());
                    updatedBook.setAuthor(authorField.getText());
                    updatedBook.setReleaseDate(publishDateField.getText());
                    library.getBookServices().updateBook(updatedBook);
                    updateBookList();
                    return updatedBook;
                }
                return null;
            });
            dialog.showAndWait();
        }
    }
}
