package ru.maxed.photocleaner.core.services;

import javafx.collections.ObservableList;
import ru.maxed.photocleaner.core.entities.CheckedFile;
import ru.maxed.photocleaner.core.exeptions.TestException;

import java.io.File;
import java.util.Locale;

/*
Считывание списков оригиналов и копий из директории
 */
public class DirectoryReader {
    private DirectoryReader() {
        throw new IllegalStateException("Utility class");
    }

    public static void read(String path,
                            String originExtension, String processedExtension, ObservableList<CheckedFile> processedFileList,
                            ObservableList<CheckedFile> originFileList, boolean firstEntry) throws TestException {
        originExtension = ExtensionCorrecter.correct(originExtension);
        processedExtension = ExtensionCorrecter.correct(processedExtension);
        File directory = new File(path);
        File[] dirFiles = directory.listFiles();
        assert dirFiles != null;
        for (File file : dirFiles) {
            if (file.isDirectory()) {
                DirectoryReader.read(file.getAbsolutePath(), originExtension, processedExtension, processedFileList, originFileList, false);
            } else {
                int extensionStartIndex = file.getName().lastIndexOf(".");
                if (extensionStartIndex < 0) continue;
                String extension = file.getName().toLowerCase(Locale.ROOT).substring(extensionStartIndex);
                if (extension.equals(originExtension)) {
                    originFileList.add(new CheckedFile(file.getAbsolutePath()));
                } else if (extension.equals(processedExtension)) {
                    processedFileList.add(new CheckedFile(file.getAbsolutePath()));
                }
            }
        }
        if (firstEntry && (processedFileList.size() + originFileList.size() == 0)) {
            throw new TestException("По данному пути подходящих файлов не найдено");
        }
    }
}
