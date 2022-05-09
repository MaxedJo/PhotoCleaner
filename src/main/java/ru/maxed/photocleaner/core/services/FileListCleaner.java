package ru.maxed.photocleaner.core.services;

import javafx.collections.ObservableList;
import ru.maxed.photocleaner.LangLib;
import ru.maxed.photocleaner.core.entities.CheckedFile;
import ru.maxed.photocleaner.core.exeptions.TestException;

import java.util.Iterator;

/**
 * Утилитный клас удаления выделенных файлов.
 */
public final class FileListCleaner {
    /**
     * Закрывающий конструктор.
     */
    private FileListCleaner() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Удаление отмеченных файлов из списка.
     *
     * @param fileList Список файлов
     * @throws TestException Если не удалось удалить файл
     */
    public static void clean(final ObservableList<CheckedFile> fileList)
            throws TestException {
        Iterator<CheckedFile> iterator = fileList.iterator();
        while (iterator.hasNext()) {
            final CheckedFile file = iterator.next();
            if (file.isMustDelete()) {
                iterator.remove();
                if (!file.delete()) {
                    throw new TestException(
                            LangLib.DELETE_ERROR + " " + file.getName()
                    );
                }
            }
        }
    }
}
