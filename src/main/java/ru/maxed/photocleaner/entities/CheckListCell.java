package ru.maxed.photocleaner.entities;

import javafx.scene.control.ListCell;


public class CheckListCell extends ListCell<FilePane> {
    private FilePane filePane = new FilePane();

    public CheckListCell() {
        super();
    }

    @Override
    protected void updateItem(FilePane s, boolean b) {
        super.updateItem(s, b);
        setGraphic(null);
        if (!b && s != null) {
            setGraphic(s);
        }
    }

    @Override
    public String toString() {
        return "CheckListCell{" +
                "filePane=" + filePane +
                '}';
    }
}