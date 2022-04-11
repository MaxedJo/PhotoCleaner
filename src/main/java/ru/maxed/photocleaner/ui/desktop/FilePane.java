package ru.maxed.photocleaner.ui.desktop;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import ru.maxed.photocleaner.core.entities.CheckedFile;

public class FilePane extends BorderPane {
    private Label text = new Label();
    private CheckedFile checkedFile;
    private CheckBox check = new CheckBox();


    public FilePane(CheckedFile file, ListView<FilePane> parentList) {
        super();
        checkedFile = file;
        this.setLeft(text);
        text.setText(file.getPathFromStartDir());
        check.setSelected(file.isMustDelete());
        this.check.setOnAction(event -> file.setMustDelete(check.isSelected()));
        file.setDeleteHandler(() -> parentList.getItems().remove(this));
        file.setCheckedHandler(value -> check.setSelected(value));

        this.setRight(check);
    }

    public FilePane() {
        super();
        this.setLeft(text);
        this.setRight(check);
    }

    public void changeCheck() {
        this.check.setSelected(!this.check.isSelected());
        checkedFile.setMustDelete(check.isSelected());
    }

    public String getText() {
        return text.getText();
    }

    @Override
    public String toString() {
        return "{" +
                "text=" + this.text.getText() +
                ", check=" + this.check.isSelected() +
                '}';
    }
}
