package ru.maxed.photocleaner.utility;

import java.io.File;

public class CheckedFile extends File {
    private String pathFromStartDir;
    private String shortName ;
    private boolean mustDelete;

    public CheckedFile(String pathname) {
        super(pathname);
        this.shortName = this.getName().substring(0,this.getName().lastIndexOf("."));
        this.mustDelete = false;
    }

    public String getPathFromStartDir() {
        return pathFromStartDir;
    }

    public void setPathFromStartDir(String pathFromStartDir) {
        this.pathFromStartDir = pathFromStartDir;
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
    }
}
