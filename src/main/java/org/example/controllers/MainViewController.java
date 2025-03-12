package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.example.models.Library;
import org.example.services.LibraryService;
import org.example.utils.FileHandler;

import java.io.IOException;

public class MainViewController {
    Library library = new Library();
    FileHandler fileHandler;
    LibraryService libraryService;

    @FXML private StackPane contentPane;

    @FXML private void initialize() {
        loadBooks();
    }

    /*public void setLibrary(Library library) {
        this.library = library;
        this.fileHandler = new FileHandler(library); // Přidá FileHandler
        this.libraryService = new LibraryService(library);
        fileHandler.loadLibraryFromFile(); // Načte uložená data
    }*/

    public void loadBooks() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/views/books-view.fxml"));
            Pane view = loader.load();
            LibraryController libraryController = loader.getController();
            libraryController.setLibrary(library);
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadReaders() {
        loadView("readers-view.fxml");
    }

    private void loadView(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/views/" + fxmlFile));
            Pane view = fxmlLoader.load();
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
