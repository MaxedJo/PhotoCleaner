package ru.maxed.photocleaner.core.utility;

import ru.maxed.photocleaner.core.exeptions.TestException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Класс работы с файлом настроек.
 */
public final class Settings extends Properties {
    /**
     * Ключ настройки директории.
     */
    public static final String PATH = "path";
    /**
     * Ключ настройки расширения оригинального файла.
     */
    public static final String ORIGIN_EXTENSION = "originExtension";
    /**
     * Ключ настройки расширения файла для обработки.
     */
    public static final String PROCESSED_EXTENSION = "processedExtension";
    /**
     * Файл настроек.
     */
    private static final File SETTINGS_FILE = new File("settings.xml");
    /**
     * Храненилище настроек внутри программы.
     */
    private static final Properties PROPERTIES = new Properties();

    /**
     * Закрывающий конструктор.
     */
    private Settings() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Загрузка настроек из файла.
     *
     * @throws IOException Ошибка при чтении
     */
    public static void load() throws IOException {
        if (SETTINGS_FILE.exists()) {
            PROPERTIES.loadFromXML(new FileInputStream(SETTINGS_FILE));
        }
    }

    /**
     * Сохранение настроек.
     *
     * @throws TestException Ошибка при записи
     */
    public static void save() throws TestException {
        try (var fos = new FileOutputStream(SETTINGS_FILE)) {
            PROPERTIES.storeToXML(fos, "Базовые настройки");
        } catch (IOException e) {
            throw new TestException(e.getMessage());
        }
    }

    /**
     * Обновление конфигурации.
     *
     * @param key   Настройка
     * @param value Значение
     */
    public static void update(final String key, final String value) {
        if (value != null) {
            PROPERTIES.setProperty(key, value);
        }
    }

    /**
     * Получение знаения настройки.
     *
     * @param key Настройка
     * @return Значение настройки
     */
    public static String get(final String key) {
        return PROPERTIES.getProperty(key);
    }


}
