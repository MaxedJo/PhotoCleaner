package ru.maxed.photocleaner.ui.desktop;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ErrorStage extends PhotoStage {

    public ErrorStage(String error) {
        Label label = new Label(error);
        BorderPane box = new BorderPane();
        box.setCenter(label);
        Button button = new Button("Ок");
        box.setBottom(button);
        button.setOnAction(event -> this.close());
        Scene scene = new Scene(box, 230, 100);
        this.setTitle("Ошибка");
        this.setScene(scene);
        this.show();
    }
}
