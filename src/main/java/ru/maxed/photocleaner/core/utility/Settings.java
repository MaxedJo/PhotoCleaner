package ru.maxed.photocleaner.core.utility;

import ru.maxed.photocleaner.core.exeptions.TestException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
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
     * Храненилище настроек внутри программы.
     */
    private static final Properties PROPERTIES = new Properties();
    /**
     * Имя файла настроек.
     */
    private static final String SETTINGS_FILE_NAME = "settings.xml";
    /**
     * Места хранения настроек
     * в соответствии с режимом.
     */
    private static final Map<Mode, String> SETTINGS_PLACES = Map.of(
            Mode.ABS, SETTINGS_FILE_NAME,
            Mode.REL, Settings.PATH
                    + File.separator + SETTINGS_FILE_NAME,
            Mode.GLOBAL, System.getProperty("user.home")
                    + File.separator + ".config"
                    + File.separator + "photocleaner"
                    + File.separator + SETTINGS_FILE_NAME
    );
    /**
     * Файл настроек.
     */
    private static File settingsFile = new File(
            System.getProperty("user.home")
                    + File.separator + ".config"
                    + File.separator + "photocleaner"
                    + File.separator + SETTINGS_FILE_NAME
    );

    /**
     * Закрывающий конструктор.
     */
    private Settings() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Изменение директории хранения настроек.
     *
     * @param mode Режим хранения
     */
    public static void changeDist(final Mode mode) {
        settingsFile = new File(SETTINGS_PLACES.get(mode));
    }

    /**
     * Загрузка настроек из файла.
     *
     * @throws IOException Ошибка при чтении
     */
    public static void load() throws IOException {
        //-d
        //Запуска
        //глобал
        if (settingsFile.exists()) {
            PROPERTIES.loadFromXML(new FileInputStream(settingsFile));
        }
    }

    /**
     * Сохранение настроек.
     *
     * @throws TestException Ошибка при записи
     */
    public static void save() throws TestException {
        File parent = settingsFile.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        try (var fos = new FileOutputStream(settingsFile)) {
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

    /**
     * Режим хранения настроек.
     */
    public enum Mode {
        /**
         * Хранение в папке запуска.
         */
        ABS,
        /**
         * Хранение в рабочей директории.
         */
        REL,
        /**
         * Глобальное хранение.
         */
        GLOBAL
    }


}
