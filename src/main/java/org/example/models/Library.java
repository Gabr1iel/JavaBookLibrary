package org.example.models;

import org.example.services.BookServices;
import org.example.services.ReaderServices;

public class Library {
    private final BookServices bookServices = new BookServices();
    private final ReaderServices readerServices = new ReaderServices();

    public BookServices getBookServices() {
        return bookServices;
    }

    public ReaderServices getReaderServices() {
        return readerServices;
    }
}
