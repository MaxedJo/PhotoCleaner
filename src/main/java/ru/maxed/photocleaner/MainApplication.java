package ru.maxed.photocleaner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.maxed.photocleaner.ui.desktop.controllers.MainController;
import ru.maxed.photocleaner.ui.desktop.PhotoStage;
import ru.maxed.photocleaner.core.entities.CheckedFile;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = new PhotoStage();
        stage.setScene(scene);
        MainController controller = fxmlLoader.getController();
        stage.setOnCloseRequest(controller.getCloseEventHandler());
        stage.show();
    }
    public static ObservableList<CheckedFile> originFileList  = FXCollections.observableArrayList();
    public static ObservableList<CheckedFile> processedFileList  = FXCollections.observableArrayList();
    public static void main(String[] args) {
        launch();
//        DirectoryReader.read("C:\\Users\\vvmax\\Downloads\\wireless-industry-dev-1.7.10-main\\PhotoCleaner\\TestingDir",
//                ".png",".bmp", MainApplication.processedFileList,MainApplication.originFileList);
//        FileListComparator.compareLists(MainApplication.processedFileList,MainApplication.originFileList);
//        System.out.println(originFileList);
//        System.out.println(processedFileList);
//        FileListCleaner.clean(processedFileList);
//        System.out.println(processedFileList);
    }
}
