package ru.maxed.photocleaner;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

class CheckListCell extends ListCell<String> {
    BorderPane container = new BorderPane();
    Label text = new Label();
    CheckBox check = new CheckBox();

    public CheckListCell() {
        super();
        container.setLeft(text);
        container.setRight(check);
    }

    @Override
    protected void updateItem(String s, boolean b) {
        super.updateItem(s, b);
        setText(null);
        setGraphic(null);

        if (!b && s != null){
            text.setText(s);
            setGraphic(container);
        }
    }
}