package org.example.services;

import javafx.scene.control.Alert;
import org.example.models.Book;
import org.example.models.Reader;
import org.example.utils.AlertUtils;
import org.example.utils.BinaryFileHandler;
import org.example.utils.FileHandler;

import java.util.HashMap;
import java.util.List;

public class ReaderServices {
    private final BinaryFileHandler binaryFileHandler;
    private HashMap<String, Reader> readers;

    public ReaderServices(BinaryFileHandler binaryFileHandler) {
        this.binaryFileHandler = binaryFileHandler;
        readers = binaryFileHandler.loadContent("data/readers_data.ser", Reader.class);
    }

    public void addReader(Reader reader) {
        readers.put(reader.getId(), reader);
    }

    public void saveReaders() {
        binaryFileHandler.save("data/readers_data.ser", readers, Reader.class);
    }

    public void removeReader(Reader reader) {
        if (reader.getBorrowedBooks() != null) {
            AlertUtils.showErrorAlert("Error during reader removal", "Cant delete reader with loaned books!");
            return;
        }
        readers.remove(reader.getId());
    }

    public void updateReader(Reader reader) {
        if (readers.containsKey(reader.getId())) {
            saveReaders();
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
        return readers.values().stream().filter(reader -> reader.getName().equals(name)).findFirst().orElse(null);
    }

    public Reader findReaderByLoanedBook(Book book) {
        return readers.values().stream()
                .filter(reader -> reader.getBorrowedBooks().containsKey(book.getId())).findFirst().orElse(null);
    }

    public HashMap<String, Reader> getReaders() {
        return readers;
    }
}
