// This is an open source non-commercial project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
package ru.maxed.photocleaner.core.services;

import javafx.collections.ObservableList;
import ru.maxed.photocleaner.core.entities.CheckedFile;

/**
 * Утилитный класс для выделения файлов для обработки
 * аналогичных выделенным оригинальным файлам.
 */
public final class CopyOfOriginSelector {
    /**
     * Закрывающий конструктор.
     */
    private CopyOfOriginSelector() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Выделение файлов для обработки аналогичных выделенным оригинальным.
     *
     * @param processedFileList Список Файлов для обработки
     * @param originFileList    Список оригинальных файлов
     */
    public static void selectSame(
            final ObservableList<CheckedFile> processedFileList,
            final ObservableList<CheckedFile> originFileList) {
        for (CheckedFile originFile : originFileList) {
            if (originFile.isMustDelete()) {
                for (CheckedFile processedFile : processedFileList) {
                    if (originFile.getShortName().equals(
                            processedFile.getShortName()
                    )) {
                        processedFile.setMustDelete(true);
                    }
                }
            }
        }
    }
}
