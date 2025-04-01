package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.example.models.Genre;
import org.example.models.Library;
import org.example.services.GenreServices;
import org.example.utils.FileHandler;

public class GenreController {
    Library library;
    GenreServices genreServices;
    FileHandler fileHandler;
    @FXML TextField genreTitleField;
    @FXML TextField findGenreByTitleField;
    @FXML ListView<String> genreListView;

    public void setGenresFromLibrary(Library library) {
        this.library = library;
        this.genreServices = library.getGenreServices();
        this.fileHandler = library.getFileHandler();
        updateGenresList();
    }

    public void updateGenresList() {
        ObservableList<String> genresList = FXCollections.observableArrayList(library.getGenreServices().getGenres().stream().map(Genre::getTitle).toList());
        genreListView.setItems(genresList);
    }

    @FXML private void handleAddGenre() {
        String title = genreTitleField.getText();
        if (!title.trim().isEmpty()) {
            Genre genre = new Genre(title);
            library.getGenreServices().addGenre(genre);
            library.getFileHandler().saveGenresToFile(library.getGenreServices().getGenres());
            updateGenresList();
            genreTitleField.clear();
        }
    }

    @FXML private void handleRemoveGenre() {
        String title = genreListView.getSelectionModel().getSelectedItem();
        if (!title.trim().isEmpty()) {
            Genre genre = library.getGenreServices().getGenreByTitle(title);
            library.getGenreServices().removeGenre(genre);
            library.getFileHandler().saveGenresToFile(library.getGenreServices().getGenres());
            updateGenresList();
        }
    }

    @FXML private void handleFindGenre() {
        String title = findGenreByTitleField.getText();
        Genre genre = library.getGenreServices().getGenreByTitle(title);
        if (!title.trim().isEmpty() && genre != null) {
            genreListView.getItems().clear();
            genreListView.getItems().add(genre.getTitle());
        } else if (title.trim().isEmpty()) {
            updateGenresList();
        } else {
            genreListView.getItems().clear();
        }
    }

    @FXML private void handleEditGenre() {
        String title = genreListView.getSelectionModel().getSelectedItem();
        if (!title.trim().isEmpty()) {
            Genre genre = library.getGenreServices().getGenreByTitle(title);
            Dialog<Genre> dialog = new Dialog<>();
            dialog.setTitle("Edit Genre");
            dialog.setHeaderText("Edit Genre");

            ButtonType saveBtn = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(saveBtn, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField genreTitleField = new TextField(genre.getTitle());

            grid.add(new Label("Title"), 0, 0);
            grid.add(genreTitleField, 1, 0);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveBtn) {
                    genre.setTitle(genreTitleField.getText());
                    library.getGenreServices().editGenre(genre);
                    updateGenresList();
                    return genre;
                }
                return null;
            });
            dialog.showAndWait();
        }
    }
}
