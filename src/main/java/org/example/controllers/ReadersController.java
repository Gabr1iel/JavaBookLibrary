package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.models.Library;
import org.example.models.Reader;
import org.example.services.ReaderServices;
import org.example.utils.FileHandler;

public class ReadersController {
    Library library;
    ReaderServices readerServices;
    FileHandler fileHandler;
    @FXML private TextField nameField;
    @FXML private TextField birthDateField;
    @FXML private TextField addressField;
    @FXML private TextField findByNameField;
    @FXML private TableView<Reader> readerTable;
    @FXML private TableColumn<Reader, String> nameTableCol;
    @FXML private TableColumn<Reader, String> bDateTableCol;
    @FXML private TableColumn<Reader, String> addressTableCol;


    public void setReadersFromLibrary(Library library, FileHandler fileHandler) {
        this.library = library;
        this.readerServices = library.getReaderServices();
        this.fileHandler = fileHandler;
        fileHandler.loadReadersFromFile();
        updateReadersList();
    }

    private void updateReadersList() {
        nameTableCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        bDateTableCol.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        addressTableCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        readerTable.setItems(FXCollections.observableArrayList(readerServices.getReaders()));
    }

    public void addReader() {
        String readerName = nameField.getText();
        String birthDate = birthDateField.getText();
        String address = addressField.getText();

        if (!readerName.trim().isEmpty() && !birthDate.trim().isEmpty() && !address.trim().isEmpty()) {
            Reader newReader = new Reader(readerName, birthDate, address);
            readerServices.addReader(newReader);
            fileHandler.saveReadersToFile();
            updateReadersList();
            nameField.clear();
            birthDateField.clear();
            addressField.clear();
        }
    }

    public void removeReader() {
        Reader reader = readerTable.getSelectionModel().getSelectedItem();

        if (reader != null) {
            readerServices.removeReader(reader);
            fileHandler.saveReadersToFile();
            updateReadersList();
        }
    }

    public void findReader() {
        String name = findByNameField.getText();
        if (!name.trim().isEmpty() && name != null && library.getReaderServices().findReaderByName(name) != null) {
            Reader reader = library.getReaderServices().findReaderByName(name);
            readerTable.getItems().clear();
            readerTable.getItems().add(reader);
            findByNameField.clear();
        } else if (name.isEmpty()) {
            updateReadersList();
        } else {
            readerTable.getItems().clear();
        }
    }
}
