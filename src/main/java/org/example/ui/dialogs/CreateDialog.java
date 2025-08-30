package org.example.ui.dialogs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.example.ui.controllers.DualServiceEditController;
import org.example.ui.controllers.EditController;

import java.util.Optional;

public final class CreateDialog {
    public CreateDialog() {}

    public static <M, S, T> void showEditDialog(String title, String headerTxt, String fxmlPath, M model, S modelService, T secondaryModelService) throws Exception {
        FXMLLoader loader = new FXMLLoader(CreateDialog.class.getResource(fxmlPath));
        Parent content = loader.load();
        EditController<M, S> controller = loader.getController();

        if (controller instanceof DualServiceEditController)
            ((DualServiceEditController<M, S, T>) controller).setSecondaryService(secondaryModelService);
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

    public static <M, S> void showEditDialog(String title, String headerTxt, String fxmlPath, M model, S modelService) throws Exception {
        showEditDialog(title, headerTxt, fxmlPath, model, modelService, Optional.empty());
    }
}
