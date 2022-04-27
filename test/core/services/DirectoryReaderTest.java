package core.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.maxed.photocleaner.core.entities.CheckedFile;
import ru.maxed.photocleaner.core.exeptions.TestExeption;
import ru.maxed.photocleaner.core.services.DirectoryReader;

class DirectoryReaderTest {
    ObservableList<CheckedFile> originList =FXCollections.observableArrayList();
    ObservableList<CheckedFile> processedList =FXCollections.observableArrayList();
    @BeforeAll
    static void init() {
        Directory.create();
    }
    @BeforeEach
    void setUp(){
        originList =FXCollections.observableArrayList();
        processedList =FXCollections.observableArrayList();
    }

    @ParameterizedTest
    @CsvSource({
            ".bmp,.png", // Обыное чтение
            "bmp,png", // Расширение без точек
            ".BmP,.pnG", // Расширение с разным регистром
    })
    void read(String processedExtension, String originExtension) {
        try {
            DirectoryReader.read(Directory.testPath,originExtension,processedExtension,processedList,originList);
        } catch (TestExeption e) {
            e.printStackTrace();
        }
        System.out.println(processedExtension);
        Assertions.assertEquals(Directory.processedFilesCount,processedList.size());
        Assertions.assertEquals(Directory.originFileCount,originList.size());
    }


    @Test
    void readEmptyLists() {
        String processedExtension = ".yui";
        String originExtension = ".";
        Assertions.assertThrows(TestExeption.class,()->DirectoryReader.read(Directory.testPath,originExtension,processedExtension,processedList,originList));
    }
    @AfterAll
    static void clean(){
        Directory.clean();
    }
}