package ru.maxed.photocleaner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.Locale;

public class Directory extends File {
    public Directory(String pathname) {
        super(pathname);
    }

    ObservableList<String> getFileList(String... filter) {
        ObservableList<String> files = FXCollections.observableArrayList();
        File[] dirFiles = this.listFiles();
        assert dirFiles != null;
        for (File file : dirFiles) {
            if (file.isDirectory()) {
                files.addAll(new Directory(file.getAbsolutePath()).getFileList(filter));
            } else {
                if (filter.length == 0 || file.getName().toLowerCase(Locale.ROOT).contains(filter[0].toLowerCase(Locale.ROOT))) {
                    files.add(file.getName());
                }
            }
        }
        return files;
    }
}
