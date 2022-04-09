package ru.maxed.photocleaner.utility;

import java.io.File;

public class CheckedFile extends File {
    String pathFromStartDir;
    String shortName = this.getName().substring(0,this.getName().lastIndexOf("."));
    boolean mustDelete = false;

    public CheckedFile(String pathname) {
        super(pathname);
    }
}
