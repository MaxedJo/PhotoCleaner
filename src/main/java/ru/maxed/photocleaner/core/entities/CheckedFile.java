package ru.maxed.photocleaner.core.entities;

import java.io.File;

public class CheckedFile extends File {
    private static String mainPath;
    private String shortName;
    private boolean mustDelete;
    private onCheckedHandler checkedHandler = null;

    public interface onCheckedHandler {
        void set(boolean value);
    }

    public void setCheckedHandler(onCheckedHandler checkedHandler) {
        this.checkedHandler = checkedHandler;
    }

    public CheckedFile(String pathname) {
        super(pathname);
        this.shortName = this.getName().substring(0, this.getName().lastIndexOf("."));
        this.mustDelete = false;
    }

    public static String getMainPath() {
        return mainPath;
    }

    public String getPathFromStartDir() {
        return this.getAbsolutePath().replace(mainPath + File.separator, "");
    }

    public static void setMainPath(String mainPath) {
        CheckedFile.mainPath = mainPath;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public boolean isMustDelete() {
        return mustDelete;
    }

    public void setMustDelete(boolean mustDelete) {
        this.mustDelete = mustDelete;
        if (checkedHandler != null) {
            checkedHandler.set(mustDelete);
        }
    }

    @Override
    public String toString() {
        return "CheckedFile{" +
                "shortName='" + shortName + '\'' +
                ", mustDelete=" + mustDelete +
                '}';
    }
}
