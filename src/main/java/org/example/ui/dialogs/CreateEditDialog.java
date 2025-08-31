package org.example.ui.dialogs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.example.ui.controllers.DualServiceEditController;
import org.example.ui.controllers.EditController;

public final class CreateEditDialog<M, S, T> implements DialogUtil {
    private final String title;
    private final String headerTxt;
    private final String fxmlPath;
    private final M model;
    private final S modelService;
    private final T secondaryModelService;

    public CreateEditDialog(String title, String headerTxt, String fxmlPath, M model, S modelService) {
        this(title, headerTxt, fxmlPath, model, modelService, null);
    }

    public CreateEditDialog(String title, String headerTxt, String fxmlPath, M model, S modelService, T secondaryModelService) {
        this.title = title;
        this.headerTxt = headerTxt;
        this.fxmlPath = fxmlPath;
        this.model = model;
        this.modelService = modelService;
        this.secondaryModelService = secondaryModelService;
    }

    @Override
    public void show() throws Exception {
        FXMLLoader loader = new FXMLLoader(CreateEditDialog.class.getResource(fxmlPath));
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
}
