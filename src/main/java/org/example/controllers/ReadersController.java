package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.example.models.Book;
import org.example.models.Library;
import org.example.models.Reader;
import org.example.services.ReaderServices;
import org.example.utils.FileHandler;

import java.util.List;
import java.util.Optional;

public class ReadersController {
    Library library;
    ReaderServices readerServices;
    FileHandler fileHandler;
    @FXML private TextField nameField;
    @FXML private TextField birthDateField;
    @FXML private TextField addressField;
    @FXML private TextField findByNameField;
    @FXML private TableView<Reader> readerTable;
    @FXML private TableColumn<Reader, String> nameTableCol;
    @FXML private TableColumn<Reader, String> bDateTableCol;
    @FXML private TableColumn<Reader, String> addressTableCol;
    @FXML private TableColumn<Reader, List<Book>> loanedBooksTableCol;


    public void setReadersFromLibrary(Library library) {
        this.library = library;
        this.readerServices = library.getReaderServices();
        this.fileHandler = library.getFileHandler();
        updateReadersList();
    }

    private void updateReadersList() {
        nameTableCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        bDateTableCol.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        addressTableCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        loanedBooksTableCol.setCellValueFactory(new PropertyValueFactory<>("borrowedBooks"));
        loanedBooksTableCol.setCellFactory(column -> new BorrowedBooksCell(library));

        readerTable.setItems(FXCollections.observableArrayList(readerServices.getReaders()));
    }

    public void addReader() {
        String readerName = nameField.getText();
        String birthDate = birthDateField.getText();
        String address = addressField.getText();

        if (!readerName.trim().isEmpty() && !birthDate.trim().isEmpty() && !address.trim().isEmpty()) {
            Reader newReader = new Reader(readerName, birthDate, address);
            readerServices.addReader(newReader);
            fileHandler.saveReadersToFile(library.getReaderServices().getReaders());
            updateReadersList();
            nameField.clear();
            birthDateField.clear();
            addressField.clear();
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

    @FXML public void handleLoanBook() {
        Reader reader = readerTable.getSelectionModel().getSelectedItem();
        if (reader == null) {
            System.out.println("No reader selected");
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
        dialog.setHeaderText("Choose book for reader: " + reader.getName());
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
