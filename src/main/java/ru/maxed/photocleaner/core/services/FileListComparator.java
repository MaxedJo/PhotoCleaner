package ru.maxed.photocleaner.core.services;

import javafx.collections.ObservableList;
import ru.maxed.photocleaner.core.entities.CheckedFile;

/**
 * Утилитный класс для выделения файлов для обработки
 * не имеющих аналогичных оригинальных файлов.
 */
public final class FileListComparator {
    /**
     * Закрывающий конструктор.
     */
    private FileListComparator() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Выделение файлов для обработки,
     * не имеющих аналогичных оригинальных файлов.
     *
     * @param processedFileList Список фалов для обработки
     * @param originFileList    Список оригинальных файлов
     */
    public static void compareLists(
            final ObservableList<CheckedFile> processedFileList,
            final ObservableList<CheckedFile> originFileList
    ) {
        for (CheckedFile processedFile : processedFileList) {
            if (processedFile.isMustDelete()) {
                continue;
            }
            processedFile.setMustDelete(true);
            for (CheckedFile originFile : originFileList) {
                if (processedFile.getShortName()
                        .equals(originFile.getShortName())) {
                    processedFile.setMustDelete(false);
                    break;
                }
            }
        }
    }
}
