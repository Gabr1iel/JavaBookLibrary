package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.example.models.Library;

import java.io.IOException;

public class MainViewController {
    Library library = new Library();

    @FXML private StackPane contentPane;

    @FXML private void initialize() {
        contentPane.getStylesheets().add(getClass().getResource("/org/example/styles/style.css").toExternalForm());
        loadBooks();
    }

    public void loadBooks() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/views/books-view.fxml"));
            Pane view = loader.load();
            BooksController booksController = loader.getController();
            booksController.setBooksFromLibrary(library);
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadReaders() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/views/readers-view.fxml"));
            Pane view = loader.load();
            ReadersController readersController = loader.getController();
            readersController.setReadersFromLibrary(library);
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGenres() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/views/genres-view.fxml"));
            Pane view = loader.load();
            GenreController genreController = loader.getController();
            genreController.setGenresFromLibrary(library);
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
