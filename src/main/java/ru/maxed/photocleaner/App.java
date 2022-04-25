package ru.maxed.photocleaner;

import java.io.File;

public class App {
    public static void main(String[] args) {
        MainApplication.main(args);
        File dir = new File("TestngDir");
        dir.delete();
    }
}
