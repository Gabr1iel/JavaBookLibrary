package org.example.services;

import org.example.models.Book;
import org.example.models.Reader;
import org.example.utils.FileHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReaderServices {
    private FileHandler fileHandler;
    private List<Reader> readers;

    public ReaderServices(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        readers = fileHandler.loadReadersFromFile();
    }

    public void addReader(Reader reader) {
        readers.add(reader);
    }

    public void removeReader(Reader reader) {
        readers.remove(reader);
    }

    public List<Reader> getReaders() {
        return readers;
    }

    public void setReaders(List<Reader> readers) {
        this.readers = readers;
    }

    public void loanBook(Book book, Reader reader) {
        if (book != null) {
            reader.addBook(book);
            book.borrowBook();
        }
    }

    public Reader findReaderByName(String name) {
        for (Reader reader : readers) {
            if (reader.getName().equals(name)) {
                return reader;
            }
        }
        return null;
    }
}
