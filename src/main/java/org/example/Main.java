package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controllers.MainViewController;
import org.example.services.BookServices;
import org.example.services.GenreServices;
import org.example.services.ReaderServices;
import org.example.utils.BinaryFileHandler;
import org.example.utils.FileHandler;

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