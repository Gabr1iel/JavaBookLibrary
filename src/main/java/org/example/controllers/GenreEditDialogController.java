package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.models.Genre;
import org.example.services.GenreServices;
import org.example.utils.EditController;

public class GenreEditDialogController implements EditController<GenreServices, Genre> {
    GenreServices genreServices;
    Genre selectedGenre;

    @FXML
    TextField genreTitleField;

    @Override
    public void setService(GenreServices genreService) {
        this.genreServices = genreService;
    }

    @Override
    public void setModel(Genre genre) {
        this.selectedGenre = genre;
        genreTitleField.setText(genre.getTitle());
    }

    @Override
    public void Edit() {
        selectedGenre.setTitle(genreTitleField.getText());
        genreServices.editGenre(selectedGenre);
    }
}
