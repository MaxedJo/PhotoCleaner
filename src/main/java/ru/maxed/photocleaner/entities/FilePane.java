package ru.maxed.photocleaner.entities;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class FilePane extends BorderPane {
    private Label text = new Label();
    private CheckBox check = new CheckBox();
    public FilePane(String str) {
        super();
        this.setLeft(text);
        text.setText(str);
        this.setRight(check);
    }
    public FilePane() {
        super();
        this.setLeft(text);
        this.setRight(check);
    }

    public void setCheck(boolean check) {
        this.check.setSelected(check);
    }

    public String getText() {
        return text.getText();
    }

    public boolean getCheck() {
        return check.isSelected();
    }

    @Override
    public String toString() {
        return "{" +
                "text=" + this.text.getText() +
                ", check=" + this.check.isSelected() +
                '}';
    }
}
