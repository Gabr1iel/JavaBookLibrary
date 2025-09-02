package org.example.book;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.example.genre.Genre;
import org.example.genre.GenreServices;
import org.example.ui.comboBox.CreateComboBox;
import org.example.ui.controllers.DualServiceEditController;

import java.util.ArrayList;
import java.util.List;

public class BookEditDialogController implements DualServiceEditController<Book, BookServices, GenreServices> {
    BookServices bookService;
    GenreServices genreService;
    Book selectedBook;

    @FXML TextField bookTitleField;
    @FXML TextField bookAuthorField;
    @FXML TextField bookDateField;
    @FXML ComboBox<Genre> bookGenreBox;

    @Override
    public void setModel(Book book) {
        this.selectedBook = book;
        setInitialValues(book);
    }

    @Override
    public void setService(BookServices bookServices) {
        this.bookService = bookServices;
    }

    @Override
    public void setSecondaryService(GenreServices secondaryService) {
        this.genreService = secondaryService;
    }

    public void setInitialValues(Book book) {
        bookTitleField.setText(book.getTitle());
        bookAuthorField.setText(book.getAuthor());
        bookDateField.setText(book.getReleaseDate());

        CreateComboBox.setupComboBox("Genre", bookGenreBox, genreService.getGenres().values(), Genre::getTitle);
        bookGenreBox.setValue(genreService.getGenreByTitle(book.getBookGenres().getFirst()));
    }

    @Override
    public void Edit() {
        List<String> genreList = new ArrayList<>();

        selectedBook.setTitle(bookTitleField.getText());
        selectedBook.setAuthor(bookAuthorField.getText());
        selectedBook.setReleaseDate(bookDateField.getText());
        genreList.add(bookGenreBox.getValue().getTitle());
        selectedBook.setBookGenres(genreList);
        bookService.editBook(selectedBook);
    }
}
