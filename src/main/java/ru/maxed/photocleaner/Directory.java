package ru.maxed.photocleaner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;

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
                files.addAll(new Directory(file.getAbsolutePath()).getFileList());
            } else {
                if (filter.length == 0 || file.getName().contains(filter[0])) {
                    files.add(file.getName());
                }
            }
        }
        return files;
    }
}
