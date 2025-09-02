package org.example.genre;

import org.example.common.file.BinaryFileHandler;

import java.util.HashMap;

public class GenreServices {
    private final HashMap<String, Genre> genres;
    private final BinaryFileHandler binaryFileHandler;

    public GenreServices(BinaryFileHandler binaryFileHandler) {
        this.binaryFileHandler = binaryFileHandler;
        this.genres = binaryFileHandler.loadContent("data/genres_data.ser", Genre.class);
    }

    public void saveGenres() {
        binaryFileHandler.save("data/genres_data.ser", genres, Genre.class);
    }

    public void createGenre(String title) {
        if (!title.trim().isEmpty()) {
            Genre newGenre = new Genre(title);
            genres.put(newGenre.getId(), newGenre);
            saveGenres();
        }
    }

    public void removeGenre(String title) {
        if (!title.trim().isEmpty()) {
            genres.remove(getGenreByTitle(title).getId());
            saveGenres();
        }
    }

    public void editGenre(Genre genre) {
        if (genres.containsKey(genre.getId())) {
            saveGenres();
        }
    }

    public Genre getGenreByTitle(String title) {
        return genres.values().stream()
                .filter(genre -> genre.getTitle().equalsIgnoreCase(title)).findFirst().orElse(null);
    }

    public HashMap<String, Genre> getGenres() {
        return genres;
    }
}
