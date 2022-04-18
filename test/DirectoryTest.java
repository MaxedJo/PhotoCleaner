
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;



class DirectoryTest {
    private String testPath = "TestingDir";
    ObservableList<String> files = FXCollections.observableArrayList();
    int fileCount = 0;
    int secondaryFilesCount = 0;
    String absolutePath;
    @BeforeEach
    void setUp() {
        File dir = new File(testPath);
        dir.mkdir();
        absolutePath=dir.getAbsolutePath();
        for (int i = 0; i < 100; i++) {
            File file = new File(testPath+"/Test"+i+".png");
            try {
                file.createNewFile();
                fileCount++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 100; i+=2) {
            File file = new File(testPath+"/Test"+i+".bMp");
            try {
                file.createNewFile();
                fileCount++;
                secondaryFilesCount++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File secondaryDir = new File(testPath+"/Something");
        secondaryDir.mkdir();
        for (int i = 100; i < 200; i+=4) {
            File file = new File(testPath+"/Something/Test"+i+".bmp");
            try {
                file.createNewFile();
                fileCount++;
                secondaryFilesCount++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 100; i < 200; i+=4) {
            File file = new File(testPath+"/Something/Test"+i+".dox");
            try {
                file.createNewFile();
                fileCount++;
                secondaryFilesCount++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void getFileListTest() {

    }
    @Test
    void getFilteredFileListTest() {

    }
}