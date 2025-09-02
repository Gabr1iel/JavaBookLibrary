package org.example.reader;

import org.example.book.Book;
import org.example.book.BookServices;
import org.example.exceptions.LoanException;
import org.example.ui.alerts.AlertUtils;
import org.example.common.file.BinaryFileHandler;
import org.example.ui.dialogs.CreateChoiceDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ReaderServices {
    private final BinaryFileHandler binaryFileHandler;
    private final HashMap<String, Reader> readers;

    public ReaderServices(BinaryFileHandler binaryFileHandler) {
        this.binaryFileHandler = binaryFileHandler;
        readers = binaryFileHandler.loadContent("data/readers_data.ser", Reader.class);
    }

    public void saveReaders() {
        binaryFileHandler.save("data/readers_data.ser", readers, Reader.class);
    }

    public void createReader(String name, String email, String address) {
        if (!name.trim().isEmpty() && !email.trim().isEmpty() && !address.trim().isEmpty()) {
            Reader newReader = new Reader(name, email, address);
            readers.put(newReader.getId(), newReader);
            saveReaders();

        } else {
            AlertUtils.showErrorAlert("Missing Information!", "Please fill out all information about reader!");
        }
    }

    public void removeReader(Reader reader) {
        if (reader.getBorrowedBooks() != null) {
            AlertUtils.showErrorAlert("Error during reader removal", "Cant delete reader with loaned books!");
            return;
        }
        readers.remove(reader.getId());
        saveReaders();
    }

    public void editReader(Reader reader) {
        if (readers.containsKey(reader.getId())) {
            saveReaders();
        }
    }

    public void loanBook(Book selectedBook, Reader reader) {
        reader.addBook(selectedBook);
        selectedBook.borrowBook();
        saveReaders();
    }

    public void returnLoanedBook(Book book) {
        if (book != null) {
            Reader reader = findReaderByLoanedBook(book);
            reader.removeBook(book);
            book.returnBook();
            saveReaders();
        }
    }

    public void validateLoan(Reader reader, List<Book> books) throws LoanException {
        if (reader == null)
            throw new LoanException("You have to select a reader!");
        if (books.isEmpty())
            throw new LoanException("No available books found");
        if (reader.getBorrowedBooks().size() >= 3)
            throw new LoanException("Reader has already the maximum number of books loaned!");
    }

    public Reader findReaderByName(String name) {
        return readers.values().stream().filter(reader -> reader.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public Reader findReaderByLoanedBook(Book book) {
        return readers.values().stream()
                .filter(reader -> reader.getBorrowedBooks().contains(book.getTitle())).findFirst().orElse(null);
    }

    public HashMap<String, Reader> getReaders() {
        return readers;
    }
}
