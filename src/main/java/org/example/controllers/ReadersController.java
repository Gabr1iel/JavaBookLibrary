package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.example.models.Book;
import org.example.models.Library;
import org.example.models.Reader;
import org.example.services.ReaderServices;
import org.example.utils.AlertUtils;
import org.example.utils.FileHandler;

import java.util.List;
import java.util.Optional;

public class ReadersController {
    Library library;
    ReaderServices readerServices;
    FileHandler fileHandler;
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField addressField;
    @FXML private TextField findByNameField;
    @FXML private TextField findByLoanedBookField;
    @FXML private TableView<Reader> readerTable;
    @FXML private TableColumn<Reader, String> nameTableCol;
    @FXML private TableColumn<Reader, String> emailTableCol;
    @FXML private TableColumn<Reader, String> addressTableCol;
    @FXML private TableColumn<Reader, List<Book>> loanedBooksTableCol;


    public void setReadersFromLibrary(Library library) {
        this.library = library;
        this.readerServices = library.getReaderServices();
        this.fileHandler = library.getFileHandler();
        updateReadersList();
    }

    @FXML public  void initialize() {
        readerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void updateReadersList() {
        nameTableCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailTableCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressTableCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        loanedBooksTableCol.setCellValueFactory(new PropertyValueFactory<>("borrowedBooks"));
        loanedBooksTableCol.setCellFactory(column -> new BorrowedBooksCell(library));

        readerTable.setItems(FXCollections.observableArrayList(readerServices.getReaders()));
    }

    public void addReader() {
        String readerName = nameField.getText();
        String email = emailField.getText();
        String address = addressField.getText();

        if (!readerName.trim().isEmpty() && !email.trim().isEmpty() && !address.trim().isEmpty()) {
            Reader newReader = new Reader(readerName, email, address);
            readerServices.addReader(newReader);
            fileHandler.saveReadersToFile(library.getReaderServices().getReaders());
            updateReadersList();
            nameField.clear();
            emailField.clear();
            addressField.clear();
        } else {
            AlertUtils.showErrorAlert("Missing Information!", "Please fill out all information about reader!");
        }
    }

    public void removeReader() {
        Reader reader = readerTable.getSelectionModel().getSelectedItem();

        if (reader != null) {
            readerServices.removeReader(reader);
            fileHandler.saveReadersToFile(library.getReaderServices().getReaders());
            updateReadersList();
        }
    }

    public void findReader() {
        String name = findByNameField.getText();
        if (!name.trim().isEmpty() && name != null && library.getReaderServices().findReaderByName(name) != null) {
            Reader reader = library.getReaderServices().findReaderByName(name);
            readerTable.getItems().clear();
            readerTable.getItems().add(reader);
            findByNameField.clear();
        } else if (name.isEmpty()) {
            updateReadersList();
        } else {
            readerTable.getItems().clear();
        }
    }

    public void handleUpdateReader() {
        Reader updatedReader = readerTable.getSelectionModel().getSelectedItem();
        if (updatedReader != null) {
            Dialog<Reader> dialog = new Dialog<>();
            dialog.setTitle("Edit Reader");
            dialog.setHeaderText("Edit Reader information");

            ButtonType saveBtn = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(saveBtn, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField nameField = new TextField(updatedReader.getName());
            TextField emailField = new TextField(updatedReader.getEmail());
            TextField addressField = new TextField(updatedReader.getAddress());

            grid.add(new Label("Name"), 0, 0);
            grid.add(nameField, 1, 0);
            grid.add(new Label("Email"), 0, 1);
            grid.add(emailField, 1, 1);
            grid.add(new Label("Address"), 0, 2);
            grid.add(addressField, 1, 2);

            dialog.getDialogPane().setContent(grid);
            dialog.setResultConverter(button -> {
                if (button == saveBtn) {
                    updatedReader.setName(nameField.getText());
                    updatedReader.setEmail(emailField.getText());
                    updatedReader.setAddress(addressField.getText());
                    library.getReaderServices().updateReader(updatedReader);
                    updateReadersList();
                    return updatedReader;
                }
                return null;
            });
            dialog.showAndWait();
        }
    }

    @FXML public void handleLoanBook() {
        Reader reader = readerTable.getSelectionModel().getSelectedItem();
        if (reader == null) {
            AlertUtils.showErrorAlert("Error during loan", "You have to select a reader!");
            return;
        } else if (reader.getBorrowedBooks().size() == 3) {
            AlertUtils.showErrorAlert("Error during loan", "Reader has already the maximum number of books loaned!");
            return;
        }
        List<Book> avilableBooks = library.getBookServices().getAvilableBooks();
        List<String> booksTitle = avilableBooks.stream().map(Book::getTitle).toList();
        if (avilableBooks.isEmpty()) {
            System.out.println("No available books");
            return;
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(booksTitle.get(0), booksTitle);
        dialog.setTitle("Loan book");
        dialog.setHeaderText("Choose book for updatedReader: " + reader.getName());
        dialog.setContentText("book: ");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            Book selectedbook = library.getBookServices().findBookByTitle(result.get());
            library.getReaderServices().loanBook(selectedbook, reader);
            fileHandler.saveReadersToFile(library.getReaderServices().getReaders());
            fileHandler.saveBooksToFile(library.getBookServices().getBooks());
            updateReadersList();
        };
    }

    @FXML private void findReaderByLoanedBook() {
        String bookTitle = findByLoanedBookField.getText();
        Book book = library.getBookServices().findBookByTitle(bookTitle);
        if (book == null) {
            updateReadersList();
            return;
        }
        Reader reader = library.getReaderServices().findReaderByLoanedBook(book);

        if (reader != null && !bookTitle.trim().isEmpty() && bookTitle != null) {
            readerTable.getItems().clear();
            readerTable.getItems().add(reader);
            findByLoanedBookField.clear();
        } else if (bookTitle.isEmpty()) {
            updateReadersList();
        } else {
            readerTable.getItems().clear();
        }
    }

    private static class BorrowedBooksCell extends TableCell<Reader, List<Book>> {
        private final ComboBox<String> comboBox = new ComboBox<>();
        private final Button returnButton = new Button("Return");

        public BorrowedBooksCell(Library library) {
            comboBox.setPrefHeight(20);
            comboBox.setPromptText("Borrowed Books");

            returnButton.setOnAction(event -> {
                Book selectedBook = library.getBookServices().findBookByTitle(comboBox.getValue());
                if (selectedBook != null) {
                    Reader reader = getTableView().getItems().get(getIndex());
                    library.getReaderServices().returnLoanedBook(selectedBook, reader);
                    library.getBookServices().makeBookAvilable(selectedBook);
                    comboBox.getItems().remove(selectedBook);
                    comboBox.setValue(null);

                    getTableView().refresh();
                }
            });
            HBox layout = new HBox(5, comboBox, returnButton);
            setGraphic(layout);
        }

        @Override
        protected void updateItem(List<Book> books, boolean empty) {
            super.updateItem(books, empty);

            if (empty || books == null || books.isEmpty()) {
                setGraphic(null);
            } else {
                List<String> bookTitles = books.stream().map(Book::getTitle).toList();
                comboBox.getItems().setAll(bookTitles);
                setGraphic(new HBox(5, comboBox, returnButton));
            }
        }
    }
}
