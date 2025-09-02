package org.example.ui.table;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import org.example.book.Book;
import org.example.book.BookServices;
import org.example.reader.Reader;
import org.example.reader.ReaderServices;
import java.util.HashSet;

public class ReaderBooksTableCell extends TableCell<Reader, HashSet<String>> {
    private final ComboBox<String> comboBox = new ComboBox<>();
    private final Button returnButton = new Button("Return");

    public ReaderBooksTableCell(BookServices bookServices, ReaderServices readerServices) {
        comboBox.setPrefHeight(20);
        comboBox.setPromptText("Borrowed Books");

        returnButton.setOnAction(event -> {
            Book selectedBook = bookServices.findBookByTitle(comboBox.getValue());
            readerServices.returnLoanedBook(selectedBook);
            bookServices.saveBooks();
            comboBox.getItems().remove(selectedBook.getTitle());
            getTableView().refresh();
        });
        HBox layout = new HBox(5, comboBox, returnButton);
        setGraphic(layout);
    }

    @Override
    protected void updateItem(HashSet<String> books, boolean empty) {
        super.updateItem(books, empty);

        if (empty || books == null || books.isEmpty()) {
            setGraphic(null);
        } else {
            comboBox.getItems().setAll(books);
            setGraphic(new HBox(5, comboBox, returnButton));
        }
    }
}
