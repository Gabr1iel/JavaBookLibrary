package org.example.genre;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.ui.dialogs.CreateDialog;

public class GenreController {
    GenreServices genreServices;

    @FXML TextField genreTitleField;
    @FXML TextField findGenreByTitleField;
    @FXML ListView<String> genreListView;

    public void setGenreController(GenreServices genreServices) {
        this.genreServices = genreServices;
        updateGenresList();
    }

    public void updateGenresList() {
        ObservableList<String> genresList = FXCollections.observableArrayList(genreServices.getGenres().values().stream().map(Genre::getTitle).toList());
        genreListView.setItems(genresList);
    }

    @FXML private void handleAddGenre() {
        String title = genreTitleField.getText();
        genreServices.createGenre(title);
        updateGenresList();
        genreTitleField.clear();
    }

    @FXML private void handleRemoveGenre() {
        String title = genreListView.getSelectionModel().getSelectedItem();
        genreServices.removeGenre(title);
        updateGenresList();
    }

    @FXML private void handleEditGenre() {
        String title = genreListView.getSelectionModel().getSelectedItem();
        if (!title.trim().isEmpty()) {
            Genre selectedGenre = genreServices.getGenreByTitle(title);

            try {
                CreateDialog.showEditDialog("Edit Genre", "Edit Genre", "/org/example/views/edit-genre-view.fxml", selectedGenre, genreServices);
                updateGenresList();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML private void handleFindGenre() {
        String title = findGenreByTitleField.getText();
        Genre genre = genreServices.getGenreByTitle(title);
        if (!title.trim().isEmpty() && genre != null) {
            genreListView.getItems().clear();
            genreListView.getItems().add(genre.getTitle());
        } else if (title.trim().isEmpty()) {
            updateGenresList();
        } else {
            genreListView.getItems().clear();
        }
    }
}
