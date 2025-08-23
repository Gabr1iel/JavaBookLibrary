package org.example.models;

import org.example.services.BookServices;
import org.example.services.GenreServices;
import org.example.services.ReaderServices;
import org.example.utils.BinaryFileHandler;
import org.example.utils.FileHandler;

import java.util.List;

public class Library {
    private final FileHandler fileHandler = new BinaryFileHandler();
    //private final BookServices bookServices = new BookServices(fileHandler);
    private final ReaderServices readerServices = new ReaderServices(fileHandler);
    private final GenreServices genreServices = new GenreServices(fileHandler);

    public FileHandler getFileHandler() {
        return fileHandler;
    }

    public ReaderServices getReaderServices() {
        return readerServices;
    }

    public GenreServices getGenreServices() { return genreServices; }
}
