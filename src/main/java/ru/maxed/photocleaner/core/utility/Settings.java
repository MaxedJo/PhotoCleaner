package ru.maxed.photocleaner.core.utility;

import ru.maxed.photocleaner.core.exeptions.TestException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.EnumMap;
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
    private static final Map<Mode, File> SETTINGS_PLACES =
            new EnumMap<>(Mode.class);
    /**
     * Инициализированы ли настройки.
     */
    private static boolean isInitialised = false;
    /**
     * Файл настроек.
     */
    private static File settingsFile;
    /**
     * Режим сохранени насроек.
     */
    private static Mode mode = Mode.GLOBAL;

    /**
     * Закрывающий конструктор.
     */
    private Settings() {
        throw new IllegalStateException("Utility class");
    }

    private static void init() {
        SETTINGS_PLACES.put(Mode.ABS, new File(SETTINGS_FILE_NAME));
        SETTINGS_PLACES.put(Mode.GLOBAL, new File(
                System.getProperty("user.home")
                        + File.separator + ".config"
                        + File.separator + "photocleaner"
                        + File.separator + SETTINGS_FILE_NAME
        ));
        SETTINGS_PLACES.put(Mode.REL, new File(
                PROPERTIES.getProperty(Settings.PATH)
                        + File.separator + SETTINGS_FILE_NAME
        ));
        settingsFile = SETTINGS_PLACES.get(Mode.GLOBAL);
        isInitialised = true;
    }

    /**
     * Изменение директории хранения настроек.
     *
     * @param modeInit Режим хранения
     */
    public static void changeDist(final Mode modeInit) {
        mode = modeInit;
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
        if (!isInitialised) {
            init();
        }
        settingsFile = SETTINGS_PLACES.get(mode);
        if (settingsFile.exists()) {
            PROPERTIES.loadFromXML(new FileInputStream(settingsFile));
            return;
        }
        if (mode.equals(Mode.REL) && SETTINGS_PLACES.get(Mode.ABS).exists()) {
            PROPERTIES.loadFromXML(
                    new FileInputStream(SETTINGS_PLACES.get(Mode.ABS))
            );
        }
    }


    /**
     * Сохранение настроек.
     *
     * @throws TestException Ошибка при записи
     */
    public static void save() throws TestException {
        settingsFile = SETTINGS_PLACES.get(mode);
        File parent = settingsFile.getParentFile();
        if (parent != null && !parent.exists() && mode == Mode.GLOBAL) {
            boolean isCreated = parent.mkdirs();
            if (!isCreated) {
                throw new TestException(
                        "Не удалось создать расположение для папки настроек"
                );
            }
        }
        if (mode == Mode.REL && (parent == null || !parent.exists())) {
            return;
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
            if (key.equals(Settings.PATH) && !value.equals("")) {
                SETTINGS_PLACES.put(Mode.REL, new File(
                        value + File.separator + SETTINGS_FILE_NAME
                ));
            }
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
