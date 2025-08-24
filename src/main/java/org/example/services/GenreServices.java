package org.example.services;

import org.example.models.Genre;
import org.example.utils.BinaryFileHandler;
import org.example.utils.FileHandler;

import java.util.HashMap;
import java.util.List;

public class GenreServices {
    private HashMap<String, Genre> genres;
    private final BinaryFileHandler binaryFileHandler;

    public GenreServices(BinaryFileHandler binaryFileHandler) {
        this.binaryFileHandler = binaryFileHandler;
        this.genres = binaryFileHandler.loadContent("data/genres_data.ser", Genre.class);
    }

    public void addGenre(Genre genre) {
        genres.put(genre.getId(), genre);
    }

    public void saveGenres() {
        binaryFileHandler.save("data/genres_data.ser", genres, Genre.class);
    }

    public void removeGenre(Genre genre) {
        genres.remove(genre.getId());
    }

    public Genre getGenreByTitle(String title) {
        return genres.values().stream()
                .filter(genre -> genre.getTitle().equals(title)).findFirst().orElse(null);
    }

    public void editGenre(Genre genre) {
        if (genres.containsKey(genre.getId())) {
            saveGenres();
        }
    }

    public HashMap<String, Genre> getGenres() {
        return genres;
    }
}
