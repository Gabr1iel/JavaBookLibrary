package org.example.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public final class CreateDialog {
    public CreateDialog() {}

    public static <S, M> void showEditDialog(String title, String headerTxt, String fxmlPath, S modelService, M model) throws Exception {
        FXMLLoader loader = new FXMLLoader(CreateDialog.class.getResource(fxmlPath));
        Parent content = loader.load();
        EditController<S, M> controller = loader.getController();
        controller.setModel(model);
        controller.setService(modelService);

        Dialog<M> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(headerTxt);

        ButtonType saveBtn = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveBtn, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(content);
        

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveBtn)
                controller.Edit();
            return null;
        });

        dialog.showAndWait();
    }
}
