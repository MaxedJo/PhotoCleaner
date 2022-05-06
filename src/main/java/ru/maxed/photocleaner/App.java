package ru.maxed.photocleaner;

import ru.maxed.photocleaner.core.exeptions.TestException;
import ru.maxed.photocleaner.core.utility.Settings;

import java.io.IOException;

/**
 * Главный класс программы.
 */
public final class App {
    /**
     * Закрывающий конструктор.
     */
    private App() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Старт программы.
     *
     * @param args Стартовые аргументы
     */
    public static void main(final String[] args)
            throws TestException, IOException {
        String path = null;
        Settings.load();
        int i = 0;
        while (i < args.length - 1) {
            if ("-d".equals(args[i])) {
                path = args[++i];
            } else if ("-s".equals(args[i])) {
                String set = args[++i];
                if (set.equals(Settings.Mode.ABS.toString())) {
                    Settings.changeDist(Settings.Mode.ABS);
                    Settings.load();
                } else if (set.equals(Settings.Mode.REL.toString())) {
                    if (path != null) {
                        Settings.update(Settings.PATH, path);
                    }
                    Settings.changeDist(Settings.Mode.REL);
                    Settings.load();
                }
            }
            i++;
        }
        Settings.update(Settings.PATH, path);
//        ResourceBundle UI_LANGUAGE = ResourceBundle.getBundle("lang", CharsetControl.UTF_8);
//        System.out.println(UI_LANGUAGE.getString("name"));
        MainApplication.main(args);
        Settings.save();
    }
}
