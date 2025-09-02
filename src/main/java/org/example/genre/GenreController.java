package org.example.genre;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.ui.dialogs.CreateEditDialog;

public class GenreController {
    GenreServices genreServices;

    @FXML TextField genreTitleField;
    @FXML TextField findGenreByTitleField;
    @FXML ListView<String> genreListView;

    public void setupGenreController(GenreServices genreServices) {
        this.genreServices = genreServices;
        refreshGenresList();
    }

    public void refreshGenresList() {
        genreListView.setItems(FXCollections.observableArrayList(genreServices.getGenres().values().stream().map(Genre::getTitle).toList()));
    }

    @FXML private void handleAddGenre() {
        String title = genreTitleField.getText();
        genreServices.createGenre(title);
        refreshGenresList();
        genreTitleField.clear();
    }

    @FXML private void handleRemoveGenre() {
        String title = genreListView.getSelectionModel().getSelectedItem();
        genreServices.removeGenre(title);
        refreshGenresList();
    }

    @FXML private void handleEditGenre() {
        String title = genreListView.getSelectionModel().getSelectedItem();
        if (!title.trim().isEmpty()) {
            Genre selectedGenre = genreServices.getGenreByTitle(title);
            try {
                new CreateEditDialog<>("Edit Genre", "Edit Genre", "/org/example/views/edit-genre-view.fxml", selectedGenre, genreServices).show();
                refreshGenresList();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML private void handleFindGenre() {
        String title = findGenreByTitleField.getText();
        Genre genre = genreServices.getGenreByTitle(title);
        if (!title.trim().isEmpty() && genre != null)
            genreListView.getItems().setAll(genre.getTitle());
        else if (title.trim().isEmpty())
            refreshGenresList();
        else
            genreListView.getItems().clear();
    }
}
