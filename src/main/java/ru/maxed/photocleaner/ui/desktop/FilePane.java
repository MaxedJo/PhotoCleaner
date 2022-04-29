package ru.maxed.photocleaner.ui.desktop;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import ru.maxed.photocleaner.core.entities.CheckedFile;

public class FilePane extends BorderPane {
    private final Label text = new Label();
    private CheckedFile checkedFile;
    private final CheckBox check = new CheckBox();
    ToggleButton filter;
    ListView<FilePane> list;

    public FilePane(CheckedFile file, ListView<FilePane> parentList, ToggleButton buttonFilter) {
        super();
        checkedFile = file;
        this.filter = buttonFilter;
        this.setLeft(text);
        this.list = parentList;
        text.setText(file.getPathFromStartDir());
        check.setSelected(file.isMustDelete());
        this.check.setOnAction(event -> file.setMustDelete(check.isSelected()));
        file.setDeleteHandler(() -> parentList.getItems().remove(this));
        file.setCheckedHandler(this::setSelected);
        this.setRight(check);
    }

    public void setSelected(boolean checked) {
        this.check.setSelected(checked);
        removeFiltered();
    }

    private void removeFiltered() {
        if (filter.isSelected() && !check.isSelected()) {
            list.getItems().remove(this);
        }
    }

    public FilePane() {
        super();
        this.setLeft(text);
        this.setRight(check);
    }

    public void changeCheck() {
        this.check.setSelected(!this.check.isSelected());
        checkedFile.setMustDelete(check.isSelected());
        removeFiltered();
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
