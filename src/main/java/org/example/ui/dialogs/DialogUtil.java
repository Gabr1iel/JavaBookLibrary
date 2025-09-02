package org.example.ui.dialogs;

import javafx.scene.control.Dialog;

public interface DialogUtil<R> {
    Dialog<R> build() throws Exception;
}
