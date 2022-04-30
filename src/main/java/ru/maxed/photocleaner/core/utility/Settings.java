package ru.maxed.photocleaner.core.utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

public final class Settings implements Serializable {
    private Settings() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Сохранение состояния полей в файл.
     *
     * @param path               Пусть
     * @param originExtension    Исходное расширение
     * @param processedExtension Расширение файлов для обработки
     */
    public static void saveSettings(
            final String path,
            final String originExtension,
            final String processedExtension
    ) {
        Properties prop = new Properties();
        prop.setProperty("path", path);
        prop.setProperty("originExtension", originExtension);
        prop.setProperty("processedExtension", processedExtension);
        try (FileOutputStream outputStream =
                     new FileOutputStream("settings.xml")) {
            prop.storeToXML(outputStream, "BaseSettings");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Загрузка конфигурации из файла.
     *
     * @return Конфигурацию в виде Properties
     */
    public static Properties loadSettings() {
        Properties property = new Properties();
        try (FileInputStream fis = new FileInputStream("settings.properties")) {
            property.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return property;
    }

}
