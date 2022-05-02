package ru.maxed.photocleaner.core.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.maxed.photocleaner.core.entities.CheckedFile;
import ru.maxed.photocleaner.core.exeptions.TestException;

class DirectoryReaderTest {
    ObservableList<CheckedFile> originList = FXCollections.observableArrayList();
    ObservableList<CheckedFile> processedList = FXCollections.observableArrayList();

    @BeforeAll
    static void init() {
        Directory.create();
    }

    @AfterAll
    static void clean() {
        Directory.clean();
    }

    @BeforeEach
    void setUp() {
        originList = FXCollections.observableArrayList();
        processedList = FXCollections.observableArrayList();
    }

    @ParameterizedTest
    @CsvSource({
            ".bmp,.png", // Обыное чтение
            "bmp,png", // Расширение без точек
            ".BmP,.pnG", // Расширение с разным регистром
    })
    void read(String processedExtension, String originExtension) {
        try {
            DirectoryReader.read(Directory.testPath, originExtension, processedExtension, processedList, originList, true);
        } catch (TestException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(Directory.processedFilesCount, processedList.size());
        Assertions.assertEquals(Directory.originFileCount, originList.size());
    }

    @Test
    void readEmptyLists() {
        String processedExtension = ".yui";
        String originExtension = ".";
        Assertions.assertThrows(TestException.class, () -> DirectoryReader.read(Directory.testPath, originExtension, processedExtension, processedList, originList, true));
    }
}