package ru.maxed.photocleaner.core.entities;

import java.io.File;
import java.util.Objects;

public class CheckedFile extends File {
    private static String mainPath;
    private final String shortName;
    private boolean mustDelete;
    private transient OnCheckedHandle checkedHandler = null;
    private transient OnDeleteHandler deleteHandler = null;

    public CheckedFile(String pathname) {
        super(pathname);
        this.shortName = this.getName().substring(0, this.getName().lastIndexOf("."));
        this.mustDelete = false;
    }

    public CheckedFile(String pathname, boolean delete) {
        super(pathname);
        this.shortName = this.getName().substring(0, this.getName().lastIndexOf("."));
        this.mustDelete = delete;
    }

    public static void setMainPath(String mainPath) {
        CheckedFile.mainPath = mainPath;
    }

    public void setCheckedHandler(OnCheckedHandle checkedHandler) {
        this.checkedHandler = checkedHandler;
    }

    public String getPathFromStartDir() {
        return this.getAbsolutePath().replace(mainPath + File.separator, "");
    }

    public String getShortName() {
        return shortName;
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
    public boolean delete() {
        if (deleteHandler != null) {
            deleteHandler.delete();
        }
        return super.delete();
    }

    public void setDeleteHandler(OnDeleteHandler deleteHandler) {
        this.deleteHandler = deleteHandler;
    }

    @Override
    public String toString() {
        return "CheckedFile{" +
                "shortName='" + shortName + '\'' +
                ", mustDelete=" + mustDelete +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CheckedFile that = (CheckedFile) o;
        return mustDelete == that.mustDelete;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mustDelete);
    }

    public interface OnDeleteHandler {
        void delete();
    }

    public interface OnCheckedHandle {
        void set(boolean value);
    }
}
