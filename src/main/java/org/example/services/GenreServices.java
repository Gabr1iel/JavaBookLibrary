package org.example.services;

import org.example.models.Genre;
import org.example.utils.FileHandler;

import java.util.List;

public class GenreServices {
    private List<Genre> genres;
    private FileHandler fileHandler;

    public GenreServices(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        this.genres = fileHandler.loadGenresFromFile();
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    public void removeGenre(Genre genre) {
        genres.remove(genre);
    }

    public Genre getGenreByTitle(String title) {
        for (Genre genre : genres) {
            if (genre.getTitle().equals(title)) {
                return genre;
            }
        }
        return null;
    }

    public void editGenre(Genre genre) {
        for (int i = 0; i < genres.size(); i++) {
            if (genres.get(i).getId().equals(genre.getId())) {
                fileHandler.saveGenresToFile(genres);
            }
        }
    }
}
