package ru.maxed.photocleaner;

import javafx.scene.control.ListCell;


class CheckListCell extends ListCell<FilePane> {
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