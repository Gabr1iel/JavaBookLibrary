package org.example.services;

import org.example.models.Genre;
import org.example.utils.BinaryFileHandler;
import org.example.utils.FileHandler;

import java.util.List;

public class GenreServices {
    private List<Genre> genres;
    private final BinaryFileHandler binaryFileHandler;

    public GenreServices(BinaryFileHandler binaryFileHandler) {
        this.binaryFileHandler = binaryFileHandler;
        this.genres = binaryFileHandler.loadContent("data/genres_data.ser", Genre.class);
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    public void saveGenres() {
        binaryFileHandler.save("data/genres_data.ser", genres, Genre.class);
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
                saveGenres();
            }
        }
    }

    public List<Genre> getGenres() {
        return genres;
    }
}
