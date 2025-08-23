package org.example.services;

import javafx.scene.control.Alert;
import org.example.models.Book;
import org.example.models.Reader;
import org.example.utils.AlertUtils;
import org.example.utils.BinaryFileHandler;
import org.example.utils.FileHandler;

import java.util.List;

public class ReaderServices {
    private final BinaryFileHandler binaryFileHandler;
    private List<Reader> readers;

    public ReaderServices(BinaryFileHandler binaryFileHandler) {
        this.binaryFileHandler = binaryFileHandler;
        readers = binaryFileHandler.loadContent("data/readers_data.ser", Reader.class);
    }

    public void addReader(Reader reader) {
        readers.add(reader);
    }

    public void saveReaders() {
        binaryFileHandler.save("data/readers_data.ser", readers, Reader.class);
    }

    public void removeReader(Reader reader) {
        if (reader.getBorrowedBooks() != null) {
            AlertUtils.showErrorAlert("Error during reader removal", "Cant delete reader with loaned books!");
            return;
        }
        readers.remove(reader);
    }

    public void updateReader(Reader reader) {
        for (Reader r : readers) {
            if (r.getId().equals(reader.getId())) {
                saveReaders();
            }
        }
    }

    public void loanBook(Book book, Reader reader) {
        if (book != null) {
            reader.addBook(book);
            book.borrowBook();
        }
    }

    public void returnLoanedBook(Book book, Reader reader) {
        if (book != null) {
            reader.removeBook(book);
            saveReaders();
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

    public Reader findReaderByLoanedBook(Book book) {
        for (Reader reader : readers) {
            if (reader.getBorrowedBooks().stream().anyMatch(b -> b.getId().equals(book.getId()))) {
                return reader;
            }
        }
        return null;
    }

    public List<Reader> getReaders() {
        return readers;
    }

    public void setReaders(List<Reader> readers) {
        this.readers = readers;
    }
}
