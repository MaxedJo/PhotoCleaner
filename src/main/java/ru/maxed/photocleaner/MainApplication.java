package ru.maxed.photocleaner;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        InputStream iconStream = getClass().getResourceAsStream("icon.png");
        assert iconStream != null;
        Image image = new Image(iconStream);
        stage.setTitle("Photo cleaner");
        stage.getIcons().add(image);
        MainController controller = fxmlLoader.getController();
        stage.setOnCloseRequest(controller.getCloseEventHandler());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
