package ru.maxed.photocleaner.core.services;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Directory {
    static final String testPath = "TestingDir";
    static int originFileCount = 0;
    static int processedFilesCount = 0;
    static int filesCount = 0;

    static void create() {
        File dir = new File(testPath);
        if (!dir.mkdir()) {
            System.err.println("Ошибка создания");
        }
        for (int i = 0; i < 100; i++) {
            File file = new File(testPath + "/Test" + i + ".png");
            try {
                if (!file.createNewFile()) {
                    System.err.println("Ошибка создания");
                }
                originFileCount++;
                filesCount++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 100; i += 2) {
            File file = new File(testPath + "/Test" + i + ".bMp");
            try {
                if (!file.createNewFile()) {
                    System.err.println("Ошибка создания");
                }
                filesCount++;
                processedFilesCount++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 11; i += 2) {
            File file = new File(testPath + "/Test" + i + ".dox.bMp");
            try {
                if (!file.createNewFile()) {
                    System.err.println("Ошибка создания");
                }
                filesCount++;
                processedFilesCount++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File secondaryDir = new File(testPath + "/Something");
        if (!secondaryDir.mkdir()) System.err.println("Ошибка создания");
        for (int i = 100; i < 200; i += 4) {
            File file = new File(testPath + "/Something/Test" + i + ".bmp");
            try {
                if (!file.createNewFile()) {
                    System.err.println("Ошибка создания");
                }
                filesCount++;
                processedFilesCount++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 100; i < 200; i += 4) {
            File file = new File(testPath + "/Something/Test" + i + ".dox");
            try {
                if (!file.createNewFile()) {
                    System.err.println("Ошибка создания");
                }
                filesCount++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 100; i < 200; i += 3) {
            File file = new File(testPath + "/Something/Test" + i + ".bmp.dox");
            try {
                if (!file.createNewFile()) {
                    System.err.println("Ошибка создания");
                }
                filesCount++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void recursiveDelete(File file) {
        if (!file.exists()) return;
        if (file.isDirectory()) {
            for (File f :
                    Objects.requireNonNull(file.listFiles())) {
                recursiveDelete(f);
            }
        }
        if (!file.delete()) {
            System.err.println("Ошибка удаления");
        }
    }

    public static void clean() {
        File dir = new File(testPath);
        recursiveDelete(dir);
        originFileCount = 0;
        processedFilesCount = 0;
        filesCount = 0;
    }
}
