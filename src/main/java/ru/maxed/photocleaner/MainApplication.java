package ru.maxed.photocleaner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.maxed.photocleaner.controllers.MainController;
import ru.maxed.photocleaner.entities.PhotoStage;

import java.io.IOException;
import java.io.InputStream;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = new PhotoStage();
        stage.setScene(scene);
        MainController controller = fxmlLoader.getController();
        stage.setOnCloseRequest(controller.getCloseEventHandler());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
