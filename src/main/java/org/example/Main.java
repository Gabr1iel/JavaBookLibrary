package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.ui.controllers.MainViewController;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/org/example/views/main-view.fxml"));
        Parent root = fxmlloader.load();
        System.out.println("FXML načteno: " + (fxmlloader.getLocation() != null));

        MainViewController controller = fxmlloader.getController();
        System.out.println("controller: " + controller);

        Scene scene = new Scene(root);
        stage.setTitle("Library Database");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}