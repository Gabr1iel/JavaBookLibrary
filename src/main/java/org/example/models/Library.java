package org.example.models;

import org.example.services.BookServices;
import org.example.services.GenreServices;
import org.example.services.ReaderServices;
import org.example.utils.FileHandler;

public class Library {
    private final FileHandler fileHandler = new FileHandler();
    private final BookServices bookServices = new BookServices(fileHandler);
    private final ReaderServices readerServices = new ReaderServices(fileHandler);
    private final GenreServices genreServices = new GenreServices(fileHandler);

    public FileHandler getFileHandler() {
        return fileHandler;
    }

    public BookServices getBookServices() {
        return bookServices;
    }

    public ReaderServices getReaderServices() {
        return readerServices;
    }

    public GenreServices getGenreServices() { return genreServices; }
}
