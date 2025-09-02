package org.example.reader;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.book.Book;
import org.example.book.BookServices;
import org.example.exceptions.LoanException;
import org.example.ui.alerts.AlertUtils;
import org.example.ui.dialogs.CreateChoiceDialog;
import org.example.ui.dialogs.CreateEditDialog;
import org.example.ui.table.ReaderBooksTableCell;
import java.util.*;

public class ReadersController {
    ReaderServices readerServices;
    BookServices bookServices;

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField addressField;
    @FXML private TextField findByNameField;
    @FXML private TextField findByLoanedBookField;
    @FXML private TableView<Reader> readerTable;
    @FXML private TableColumn<Reader, String> nameTableCol;
    @FXML private TableColumn<Reader, String> emailTableCol;
    @FXML private TableColumn<Reader, String> addressTableCol;
    @FXML private TableColumn<Reader, HashSet<String>> loanedBooksTableCol;

    @FXML public  void initialize() {
        readerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void setupReaderController(ReaderServices readerServices, BookServices bookServices) {
        this.readerServices = readerServices;
        this.bookServices = bookServices;

        nameTableCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailTableCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressTableCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        loanedBooksTableCol.setCellValueFactory(new PropertyValueFactory<>("borrowedBooks"));
        loanedBooksTableCol.setCellFactory(column -> new ReaderBooksTableCell(bookServices, readerServices));
        readerTable.setItems(FXCollections.observableArrayList(readerServices.getReaders().values()));
    }

    private void refreshReadersTable() {
        readerTable.getItems().setAll(readerServices.getReaders().values());
        readerTable.refresh();
    }

    @FXML private void handleAddReader() {
        String name = nameField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        readerServices.createReader(name, email, address);

        refreshReadersTable();
        nameField.clear();
        emailField.clear();
        addressField.clear();
    }

    @FXML private void handleRemoveReader() {
        Reader reader = readerTable.getSelectionModel().getSelectedItem();
        if (reader != null) {
            readerServices.removeReader(reader);
            refreshReadersTable();
        }
    }

    @FXML private void handleEditReader() {
        Reader selectedReader = readerTable.getSelectionModel().getSelectedItem();
        if (selectedReader != null) {
            try {
                new CreateEditDialog<>("Edit Reader", "Edit Reader information", "/org/example/views/edit-reader-view.fxml", selectedReader, readerServices).show();
                refreshReadersTable();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML public void handleLoanBook() {
        Reader reader = readerTable.getSelectionModel().getSelectedItem();
        List<Book> availableBooks = bookServices.getAvailableBooks();

        try {
            readerServices.validateLoan(reader, availableBooks);
            Optional<Book> result = new CreateChoiceDialog<>("Loan book", "Choose book for reader: " + reader.getName(), "book: ", availableBooks, Book::getTitle).show();
            result.ifPresent(book -> {
                readerServices.loanBook(result.get(), reader);
                bookServices.saveBooks();
                refreshReadersTable();
            });
        } catch (LoanException e) {
            AlertUtils.showErrorAlert("Error during loan", e.getMessage());
        }
    }

    private void handleFind(String filterItem, Reader reader) {
        if (!filterItem.trim().isEmpty() && reader != null) {
            readerTable.getItems().setAll(Collections.singletonList(reader));
            findByNameField.clear();
        } else if (filterItem.isEmpty()) {
            refreshReadersTable();
        } else {
            readerTable.getItems().clear();
        }
    }

    @FXML private void handleFindReader() {
        String name = findByNameField.getText();
        Reader reader = readerServices.findReaderByName(name);
        handleFind(name, reader);
    }

    @FXML private void findReaderByLoanedBook() {
        String bookTitle = findByLoanedBookField.getText();
        Book book = bookServices.findBookByTitle(bookTitle);
        if (book == null) {
            refreshReadersTable();
            return;
        }
        Reader reader = readerServices.findReaderByLoanedBook(book);
        handleFind(bookTitle, reader);
    }
}
