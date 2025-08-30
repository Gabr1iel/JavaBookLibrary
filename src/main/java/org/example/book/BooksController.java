package org.example.book;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.genre.Genre;
import org.example.genre.GenreServices;
import org.example.ui.comboBox.CreateComboBox;
import org.example.ui.dialogs.CreateDialog;
import java.util.List;
import java.util.Map;

public class BooksController {
    BookServices bookServices;
    GenreServices genreServices;

    @FXML private TextField bookTitleField;
    @FXML private TextField bookAuthorField;
    @FXML private TextField bookPublishDateField;
    @FXML private TextField bookTitleFilterField;
    @FXML private TextField bookAuthorFilterField;
    @FXML private TextField bookGenreFilterField;
    @FXML private ComboBox<Genre> genreComboBox;
    @FXML private TableView<Book> bookTableView;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, String> dateColumn;
    @FXML private TableColumn<Book, String> loanedColumn;
    @FXML private TableColumn<Book, String> genreColumn;

    @FXML public void initialize() {
        bookTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void setBookController(BookServices bookServices, GenreServices genreServices) {
        this.bookServices = bookServices;
        this.genreServices = genreServices;

        CreateComboBox.setupComboBox(genreComboBox, genreServices.getGenres().values(), Genre::getTitle);
        loadBookList(); // Po nastavení knihovny rovnou načteme knihy
    }

    public void loadBookList() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        genreColumn.setCellFactory(cellData -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                Book book = getTableRow().getItem();

                if(empty || getTableRow() == null || getTableRow().getItem() == null)
                    setText(null);
                else {
                    List<String> genres = book.getBookGenres();
                    setText((genres != null && !genres.isEmpty()) ? String.join(", ", genres) : "Null");
                }
            }
        });
        loanedColumn.setCellFactory(cellData -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                Book book = getTableRow().getItem();

                if(empty || getTableRow() == null || getTableRow().getItem() == null)
                    setText(null);
                else
                    setText((book.isLoaned() ? "Loaned" : "Available"));
            }
        });

        bookTableView.setItems(FXCollections.observableArrayList(bookServices.getBooks().values()));
    }

    @FXML private void handleAddBook() {
        String title = bookTitleField.getText();
        String author = bookAuthorField.getText();
        String publishDate = bookPublishDateField.getText();
        Genre genre = genreComboBox.getValue();

        bookServices.createBook(title, author, publishDate, genre);

        loadBookList();
        bookTitleField.clear();
        bookAuthorField.clear();
        bookPublishDateField.clear();
        genreComboBox.setPromptText("Genre");
    }

    @FXML private void handleRemoveBook() {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            bookServices.removeBook(selectedBook);
            loadBookList();
        }
    }

    @FXML private void handleFilterBooks() {
        String title = bookTitleFilterField.getText();
        String author = bookAuthorFilterField.getText();
        String genre = bookGenreFilterField.getText();
        Map<String, Book> filteredBooks = bookServices.filterBooks(title, author, genre);

        if (!filteredBooks.isEmpty())
            bookTableView.getItems().setAll(filteredBooks.values());
        else if(title.trim().isEmpty() && author.trim().isEmpty() && genre.trim().isEmpty())
            loadBookList();
        else
            bookTableView.getItems().clear();
    }

    @FXML private void handleEditBook() {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            try {
                CreateDialog.showEditDialog("Update Book", "Change book information", "/org/example/views/edit-book-view.fxml", selectedBook, bookServices, genreServices);
                loadBookList();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
