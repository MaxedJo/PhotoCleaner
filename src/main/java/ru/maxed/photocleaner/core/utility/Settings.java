package ru.maxed.photocleaner.core.utility;

import ru.maxed.photocleaner.core.exeptions.TestException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

/**
 * Класс работы с файлом настроек.
 */
public final class Settings implements Serializable {
    /**
     * Закрывающий конструктор.
     */
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
    ) throws TestException {
        Properties prop = new Properties();
        prop.setProperty("path", path);
        prop.setProperty("originExtension", originExtension);
        prop.setProperty("processedExtension", processedExtension);
        try (FileOutputStream outputStream =
                     new FileOutputStream("settings.xml")) {
            prop.storeToXML(outputStream, "BaseSettings");
        } catch (IOException e) {
            throw new TestException(e.toString());
        }
    }

    /**
     * Загрузка конфигурации из файла.
     *
     * @return Конфигурацию в виде Properties
     */
    public static Properties loadSettings() throws TestException {
        Properties property = new Properties();
        try (FileInputStream fis = new FileInputStream("settings.properties")) {
            property.load(fis);
        } catch (IOException e) {
            throw new TestException(e.toString());
        }
        return property;
    }

}
