package org.example.models;

import org.example.services.BookServices;
import org.example.services.ReaderServices;
import org.example.utils.FileHandler;

public class Library {
    private final FileHandler fileHandler = new FileHandler();
    private final BookServices bookServices = new BookServices(fileHandler);
    private final ReaderServices readerServices = new ReaderServices(fileHandler);

    public FileHandler getFileHandler() {
        return fileHandler;
    }

    public BookServices getBookServices() {
        return bookServices;
    }

    public ReaderServices getReaderServices() {
        return readerServices;
    }
}
