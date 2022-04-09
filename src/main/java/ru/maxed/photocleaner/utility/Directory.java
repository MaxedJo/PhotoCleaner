package ru.maxed.photocleaner.utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.Locale;

public class Directory extends File {
    public Directory(String pathname) {
        super(pathname);
    }

    public ObservableList<String> getFileList(String mainPath, String... filter) {
        ObservableList<String> files = FXCollections.observableArrayList();
        File[] dirFiles = this.listFiles();
        assert dirFiles != null;
        for (File file : dirFiles) {
            if (file.isDirectory()) {
                files.addAll(new Directory(file.getAbsolutePath()).getFileList(mainPath, filter));
            } else {
                if (filter.length == 0 || file.getName().toLowerCase(Locale.ROOT).endsWith(filter[0].toLowerCase(Locale.ROOT))) {
                    files.add(file.getAbsolutePath().substring(mainPath.length() + 1));
                }
            }
        }
        return files;
    }
}
