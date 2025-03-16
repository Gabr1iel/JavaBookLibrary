package org.example.services;

import javafx.scene.control.Alert;
import org.example.models.Book;
import org.example.models.Reader;
import org.example.utils.AlertUtils;
import org.example.utils.FileHandler;

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
        if (reader.getBorrowedBooks() != null) {
            AlertUtils.showErrorAlert("Error during reader removal", "Cant delete reader with loaned books!");
            return;
        }
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

    public void returnLoanedBook(Book book, Reader reader) {
        if (book != null) {
            reader.removeBook(book);
            fileHandler.saveReadersToFile(readers);
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
