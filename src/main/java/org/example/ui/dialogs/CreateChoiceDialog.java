package org.example.ui.dialogs;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import org.example.ui.comboBox.CreateComboBox;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class CreateChoiceDialog<T> implements DialogUtil<T> {
    private final String title;
    private final String headerTxt;
    private final String contentTxt;
    private final List<T> items;
    private final Function<T, String> toStringFn;

    public CreateChoiceDialog(String title, String headerTxt, String contentTxt, List<T> items, Function<T, String> toStringFn) {
        this.title = title;
        this.headerTxt = headerTxt;
        this.contentTxt = contentTxt;
        this.items = items;
        this.toStringFn = toStringFn;
    }

    @Override
    public ChoiceDialog<T> build() {
        ChoiceDialog<T> dialog = new ChoiceDialog<>(items.getFirst(), items);
        ComboBox<T> comboBox = (ComboBox<T>) dialog.getDialogPane().lookup(".combo-box");
        dialog.setTitle(title);
        dialog.setHeaderText(headerTxt);
        dialog.setContentText(contentTxt);

        CreateComboBox.setupComboBox("" ,comboBox, items, toStringFn);

        return dialog;
    }

    public Optional<T> show() {
        return build().showAndWait();
    }
}
