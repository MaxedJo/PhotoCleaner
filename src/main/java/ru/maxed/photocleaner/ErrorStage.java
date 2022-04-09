package ru.maxed.photocleaner;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.InputStream;

public class ErrorStage extends Stage {

    public ErrorStage(String error) {
        Label label = new Label(error);
        VBox box = new VBox();
        box.getChildren().add(label);
        Button button = new Button("Ок");
        box.getChildren().add(button);
        button.setOnAction(event -> {
            this.close();
        });
        Scene scene = new Scene(box, 230, 100);
        this.setTitle("Ошибка");
        this.setScene(scene);
        InputStream iconStream = getClass().getResourceAsStream("icon.png");
        assert iconStream != null;
        Image image = new Image(iconStream);
        this.getIcons().add(image);
        this.show();
    }
}
