package core.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;
import ru.maxed.photocleaner.core.entities.CheckedFile;
import ru.maxed.photocleaner.core.services.DirectoryReader;

import java.io.File;
import java.io.IOException;

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

    @Test
    void read() {

        String processedExtension = ".bmp";
        String originExtension = ".png";
        DirectoryReader.read(Directory.testPath,originExtension,processedExtension,processedList,originList);
        Assertions.assertEquals(Directory.processedFilesCount,processedList.size());
        Assertions.assertEquals(Directory.originFileCount,originList.size());
    }

    @Test
    void readWithoutDots() {
        String processedExtension = "bmp";
        String originExtension = "png";
        DirectoryReader.read(Directory.testPath,originExtension,processedExtension,processedList,originList);
        Assertions.assertEquals(Directory.processedFilesCount,processedList.size());
        Assertions.assertEquals(Directory.originFileCount,originList.size());
    }
    @Test
    void readLowerUpper() {
        String processedExtension = ".BmP";
        String originExtension = "pnG";
        DirectoryReader.read(Directory.testPath,originExtension,processedExtension,processedList,originList);
        Assertions.assertEquals(Directory.processedFilesCount,processedList.size());
        Assertions.assertEquals(Directory.originFileCount,originList.size());
    }

    @Test
    void readWrongExtension() {
        String processedExtension = ".yui";
        String originExtension = ".";
        DirectoryReader.read(Directory.testPath,originExtension,processedExtension,processedList,originList);
        Assertions.assertEquals(0, processedList.size());
        Assertions.assertEquals(0, originList.size());
    }
    @AfterAll
    static void clean(){
        Directory.clean();
    }
}