package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import org.example.models.Book;
import org.example.models.Genre;
import org.example.models.Library;
import org.example.services.BookServices;
import org.example.utils.AlertUtils;
import org.example.utils.FileHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BooksController {
    Library library;
    FileHandler fileHandler;
    BookServices bookServices;

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


    public void setBooksFromLibrary(Library library) {
        this.library = library;
        this.bookServices = library.getBookServices();
        this.fileHandler = library.getFileHandler();
        genreComboBox.setItems(FXCollections.observableArrayList(library.getGenreServices().getGenres()));
        genreComboBox.setCellFactory(lc -> new ListCell<Genre>() {
            @Override
            protected void updateItem(Genre genre, boolean empty) {
                super.updateItem(genre, empty);
                setText((genre == null || empty) ? null : genre.getTitle());
            }
        });
        genreComboBox.setButtonCell(new ListCell<Genre>() {
            @Override
            protected void updateItem(Genre genre, boolean empty) {
                super.updateItem(genre, empty);
                setText((genre == null || empty) ? null : genre.getTitle());
            }
        });
        updateBookList(); // Po nastavení knihovny rovnou načteme knihy
    }

    public void updateBookList() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        genreColumn.setCellFactory(cellData -> new TableCell<Book, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if(empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                } else {
                    Book book = getTableRow().getItem();
                    if (book.getBookGenres() != null && book.getBookGenres().size() > 0) {
                        setText(book.getBookGenres().stream().map(genre -> genre.getTitle()).collect(Collectors.joining(", ")));
                    } else {
                        setText("Null");
                    }
                }
            }
        });
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
        Genre bookGenre = genreComboBox.getValue();
        List<Genre> bookGenres = new ArrayList<>();
        bookGenres.add(bookGenre);
        if (!bookTitle.trim().isEmpty() && !bookAuthor.trim().isEmpty() && !bookPublishDate.trim().isEmpty() && bookGenre != null) {
            Book newBook = new Book(bookTitle, bookAuthor, bookPublishDate, bookGenres);
            library.getBookServices().addBook(newBook);
            fileHandler.saveBooksToFile(library.getBookServices().getBooks());
            updateBookList();
            bookTitleField.clear();
            bookAuthorField.clear();
            bookPublishDateField.clear();
            genreComboBox.setPromptText("Genre");
        } else {
            AlertUtils.showErrorAlert("Missing information!","Please enter all information about book!");
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

    @FXML private void handleFindBook() {
        String title = bookTitleFilterField.getText();
        String author = bookAuthorFilterField.getText();
        String genre = bookGenreFilterField.getText();
        List<Book> filteredBooks = new ArrayList<>();

        if (title != null && !title.trim().isEmpty()) {
            filteredBooks.add(library.getBookServices().findBookByTitle(title));
        }
        if (filteredBooks.isEmpty() && author != null) {
            filteredBooks = new ArrayList<>(library.getBookServices().findBookByAuthor(author, library.getBookServices().getBooks()));
        }
        if (filteredBooks.isEmpty() && genre != null) {
            filteredBooks = new ArrayList<>(library.getBookServices().findBookByGenre(genre, library.getBookServices().getBooks()));
        }
        if (!filteredBooks.isEmpty() && genre != null && !genre.trim().isEmpty()) {
            filteredBooks = new ArrayList<>(library.getBookServices().findBookByGenre(genre, filteredBooks));
        }

        if ((title != null && !title.isEmpty()) || (author != null && !author.isEmpty()) || (genre != null && !genre.isEmpty())) {
            bookTableView.getItems().clear();
            bookTableView.getItems().addAll(filteredBooks);
        } else if((title == null || title.isEmpty()) && (author == null || author.isEmpty()) && (genre == null || genre.isEmpty())) {
            updateBookList();
        } else {
            bookTableView.getItems().clear();
        }
    }

    @FXML private void handleUpdateBook() {
        Book updatedBook = bookTableView.getSelectionModel().getSelectedItem();
        List<Genre> bookGenres = new ArrayList<>();
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
            ComboBox<Genre> genreComboBox = new ComboBox<>();
            genreComboBox.setItems(FXCollections.observableArrayList(library.getGenreServices().getGenres()));
            genreComboBox.setCellFactory(lc -> new ListCell<Genre>() {
                @Override
                protected void updateItem(Genre genre, boolean empty) {
                    super.updateItem(genre, empty);
                    setText((genre == null || empty) ? null : genre.getTitle());
                }
            });
            genreComboBox.setButtonCell(new ListCell<Genre>() {
                @Override
                protected void updateItem(Genre genre, boolean empty) {
                    super.updateItem(genre, empty);
                    setText((genre == null || empty) ? null : genre.getTitle());
                }
            });
            genreComboBox.setConverter(new StringConverter<Genre>() {
                @Override
                public String toString(Genre genre) {
                    return (genre != null) ? genre.getTitle() : "";
                }

                @Override
                public Genre fromString(String string) {
                    return new Genre(string);
                }
            });
            genreComboBox.setValue(updatedBook.getBookGenres().getFirst());

            grid.add(new Label("Title"), 0, 0);
            grid.add(titleField, 1, 0);
            grid.add(new Label("Author"), 0, 1);
            grid.add(authorField, 1, 1);
            grid.add(new Label("Publish Date"), 0, 2);
            grid.add(publishDateField, 1, 2);
            grid.add(new Label("Genre"), 0, 3);
            grid.add(genreComboBox, 1, 3);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveBtn) {
                    updatedBook.setTitle(titleField.getText());
                    updatedBook.setAuthor(authorField.getText());
                    updatedBook.setReleaseDate(publishDateField.getText());
                    bookGenres.add(genreComboBox.getValue());
                    updatedBook.setBookGenres(bookGenres);
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
