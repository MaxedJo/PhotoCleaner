package ru.maxed.photocleaner.core.utility;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ru.maxed.photocleaner.LangLib;
import ru.maxed.photocleaner.core.exeptions.TestException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Класс работы с файлом настроек.
 */
public final class Settings extends Properties {
    /**
     * Ключ настройки директории.
     */
    public static final String PATH = "path";
    public static final String LANG = "languageCode";
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
    private static final Properties PROPERTIES_GLOBAL = new Properties();
    private static final Properties PROPERTIES_RELATIVE = new Properties();
    private static final Properties PROPERTIES_ABSOLUTE = new Properties();
    /**
     * Имя файла настроек.
     */
    private static final String SETTINGS_FILE_NAME = "settings.xml";
    private static final StringProperty destination = new SimpleStringProperty();
    /**
     * Места хранения настроек
     * в соответствии с режимом.
     */
    private static EnumMap<Mode, String> SETTINGS_PLACES =
            new EnumMap<>(Map.of(Mode.GLOBAL, System.getProperty("user.home")
                    + File.separator + ".config"
                    + File.separator + "photocleaner"
                    + File.separator + SETTINGS_FILE_NAME, Mode.ABS, SETTINGS_FILE_NAME, Mode.REL, ""));
    private static ResourceBundle lang = ResourceBundle.getBundle("lang", new Locale(System.getProperty("user.language")));
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

    public static void changeDest(String dest) {
        dest = (new File(dest)).getAbsolutePath();
        destination.set(dest);
        PROPERTIES_RELATIVE.setProperty(PATH, dest);
    }

    public static String get(final String key) {
        return get(key, mode);
    }

    public static String get(final String key, Mode modeKey) {
        switch (modeKey) {
            case REL -> {
                if (PROPERTIES_RELATIVE.getProperty(key) == null || PROPERTIES_RELATIVE.getProperty(key).isBlank()) {
                    return get(key, Mode.ABS);
                } else {
                    return PROPERTIES_RELATIVE.getProperty(key);
                }
            }
            case ABS -> {
                if (PROPERTIES_ABSOLUTE.getProperty(key) == null || PROPERTIES_ABSOLUTE.getProperty(key).isBlank()) {
                    return get(key, Mode.GLOBAL);
                } else {
                    return PROPERTIES_ABSOLUTE.getProperty(key);
                }
            }
            case GLOBAL -> {
                return PROPERTIES_GLOBAL.getProperty(key);
            }
        }
        return "";
    }

    public static void load() {
        try {
            File file = new File(
                    SETTINGS_PLACES.get(Mode.GLOBAL)
            );
            if (file.exists()) {
                PROPERTIES_GLOBAL.loadFromXML(new FileInputStream(file));
            }
            file = new File(SETTINGS_PLACES.get(Mode.ABS));
            if (file.exists()) {
                PROPERTIES_ABSOLUTE.loadFromXML(new FileInputStream(file));
            }
            destination.addListener(observable -> relativeLoad());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void relativeLoad() {
        SETTINGS_PLACES.put(Mode.REL, destination.get()
                + File.separator + SETTINGS_FILE_NAME);
        try {
            File file = new File(SETTINGS_PLACES.get(Mode.REL)
            );
            if (file.exists()) {
                PROPERTIES_RELATIVE.loadFromXML(new FileInputStream(file));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ResourceBundle getLangBundle() {
        return lang;
    }

    public static void langLoad(Locale locale) {
        lang = ResourceBundle.getBundle("lang", locale);
    }

    public static String getLocalized(String key) {
        return lang.getString(key);
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
     * Сохранение настроек.
     *
     * @throws TestException Ошибка при записи
     */
    public static void save() throws TestException {
        File parent = (new File(SETTINGS_PLACES.get(Mode.GLOBAL))).getParentFile();
        if (parent != null && !parent.exists()) {
            boolean isCreated = parent.mkdirs();
            if (!isCreated) {
                throw new TestException(
                        LangLib.CONFIG_DESTINATION_ERROR.toString()
                );
            }
        }
        if (mode == Mode.REL && (parent == null || !parent.exists())) {
            return;
        }
        try (var fos = new FileOutputStream(SETTINGS_PLACES.get(Mode.GLOBAL))) {
            PROPERTIES_GLOBAL.storeToXML(fos, LangLib.CONFIG_MESSAGE.toString());
        } catch (IOException e) {
            throw new TestException(e.getMessage());
        }
        if (mode == Mode.REL) {
            try (var fos = new FileOutputStream(SETTINGS_PLACES.get(Mode.REL))) {
                PROPERTIES_RELATIVE.storeToXML(fos, LangLib.CONFIG_MESSAGE.toString());
            } catch (IOException e) {
                throw new TestException(e.getMessage());
            }

        } else if (mode == Mode.ABS) {
            try (var fos = new FileOutputStream(SETTINGS_PLACES.get(Mode.ABS))) {
                PROPERTIES_ABSOLUTE.storeToXML(fos, LangLib.CONFIG_MESSAGE.toString());
            } catch (IOException e) {
                throw new TestException(e.getMessage());
            }
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
            switch (mode) {
                case REL -> PROPERTIES_RELATIVE.setProperty(key, value);
                case ABS -> PROPERTIES_ABSOLUTE.setProperty(key, value);
                case GLOBAL -> updateGlobal(key, value);
            }
        }
    }

    public static void updateGlobal(final String key, final String value) {
        PROPERTIES_GLOBAL.setProperty(key, value);
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
