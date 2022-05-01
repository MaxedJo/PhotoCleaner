package ru.maxed.photocleaner.core.services;

import javafx.collections.ObservableList;
import ru.maxed.photocleaner.core.entities.CheckedFile;
import ru.maxed.photocleaner.core.exeptions.TestException;

import java.io.File;
import java.util.Locale;

/*
Считывание списков оригиналов и копий из директории
 */
public final class DirectoryReader {
    private DirectoryReader() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Считывание списков фалов из директории.
     *
     * @param path               Путь к директории
     * @param originExtension    Расширение оригинальных файлов
     * @param processedExtension Расширение файлов для обработки
     * @param processedFileList  Список файлов для обработки
     * @param originFileList     Список оригинальных файлов
     * @param firstEntry         Метка первого вхожждения в функцию
     * @throws TestException Ошибка если директория не содержит искомых файлов
     */
    public static void read(final String path,
                            final String originExtension,
                            final String processedExtension,
                            final ObservableList<CheckedFile> processedFileList,
                            final ObservableList<CheckedFile> originFileList,
                            final boolean firstEntry) throws TestException {
        String correctedOriginExtension =
                ExtensionCorrecter.correct(originExtension);
        String correctedProcessedExtension =
                ExtensionCorrecter.correct(processedExtension);
        File directory = new File(path);
        File[] dirFiles = directory.listFiles();
        assert dirFiles != null;
        for (File file : dirFiles) {
            if (file.isDirectory()) {
                DirectoryReader.read(
                        file.getAbsolutePath(),
                        correctedOriginExtension,
                        correctedProcessedExtension,
                        processedFileList,
                        originFileList,
                        false
                );
            } else {
                int extensionStartIndex = file.getName().lastIndexOf(".");
                if (extensionStartIndex < 0) {
                    continue;
                }
                String extension = file.getName().toLowerCase(Locale.ROOT)
                        .substring(extensionStartIndex);
                if (extension.equals(correctedOriginExtension)) {
                    originFileList.add(new CheckedFile(file.getAbsolutePath()));
                } else if (extension.equals(correctedProcessedExtension)) {
                    processedFileList.add(
                            new CheckedFile(file.getAbsolutePath())
                    );
                }
            }
        }
        if (firstEntry
                && (processedFileList.size() + originFileList.size() == 0)) {
            throw new TestException(
                    "По данному пути подходящих файлов не найдено"
            );
        }
    }
}
