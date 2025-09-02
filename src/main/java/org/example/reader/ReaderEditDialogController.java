package org.example.reader;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.ui.controllers.EditController;

public class ReaderEditDialogController implements EditController<Reader, ReaderServices> {
    Reader selectedReader;
    ReaderServices readerServices;

    @FXML TextField readerNameField;
    @FXML TextField readerEmailField;
    @FXML TextField readerAddressField;

    @Override
    public void setModel(Reader reader) {
        this.selectedReader = reader;
        setInitialValues(reader);
    }

    @Override
    public void setService(ReaderServices readerServices) {
        this.readerServices = readerServices;
    }

    public void setInitialValues(Reader reader) {
        readerNameField.setText(reader.getName());
        readerEmailField.setText(reader.getEmail());
        readerAddressField.setText(reader.getAddress());
    }

    @Override
    public void Edit() {
        selectedReader.setName(readerNameField.getText());
        selectedReader.setEmail(readerEmailField.getText());
        selectedReader.setAddress(readerAddressField.getText());
        readerServices.editReader(selectedReader);
    }
}
