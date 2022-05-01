package core.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.maxed.photocleaner.core.entities.CheckedFile;
import ru.maxed.photocleaner.core.services.MarkCleaner;

class MarkCleanerTest {
    final ObservableList<CheckedFile> originFileList = FXCollections.observableArrayList();
    final ObservableList<CheckedFile> expectedFileList = FXCollections.observableArrayList();

    @Test
    void clean() {
        originFileList.add(new CheckedFile("File1.png", false));
        originFileList.add(new CheckedFile("File2.png", true));
        originFileList.add(new CheckedFile("File3.PNg", true));
        originFileList.add(new CheckedFile("File4.PNg", false));
        expectedFileList.add(new CheckedFile("File1.png", false));
        expectedFileList.add(new CheckedFile("File2.png", false));
        expectedFileList.add(new CheckedFile("File3.PNg", false));
        expectedFileList.add(new CheckedFile("File4.PNg", false));
        MarkCleaner.clean(originFileList);
        Assertions.assertEquals(expectedFileList, originFileList);
    }
}