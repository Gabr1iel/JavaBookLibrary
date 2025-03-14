package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.example.models.Library;
import org.example.utils.FileHandler;

import java.io.IOException;

public class MainViewController {
    Library library = new Library();
    FileHandler fileHandler = new FileHandler(library);

    @FXML private StackPane contentPane;

    @FXML private void initialize() {
        loadBooks();
    }

    public void loadBooks() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/views/books-view.fxml"));
            Pane view = loader.load();
            BooksController booksController = loader.getController();
            booksController.setBooksFromLibrary(library, fileHandler);
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
            readersController.setReadersFromLibrary(library, fileHandler);
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
