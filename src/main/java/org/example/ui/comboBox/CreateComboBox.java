package org.example.ui.comboBox;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import java.util.Collection;
import java.util.function.Function;

public final class CreateComboBox {
    public CreateComboBox() {}

    public static <M> void setupComboBox(String prompt, ComboBox<M> comboBox, Collection<M> items, Function<M, String> toStringFn) {
        comboBox.setPromptText(prompt);
        comboBox.setItems(FXCollections.observableArrayList(items));
        comboBox.setCellFactory(lc -> new ListCell<>() {
            @Override
            protected void updateItem(M item, boolean empty) {
                super.updateItem(item, empty);
                setText((item == null || empty) ? null : toStringFn.apply(item));
            }
        });
        comboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(M item, boolean empty) {
                super.updateItem(item, empty);
                setText((item == null || empty) ? null : toStringFn.apply(item));
            }
        });
    }
}
