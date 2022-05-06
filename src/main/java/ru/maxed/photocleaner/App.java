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
        Settings.load();
        int i = 0;
        while (i < args.length - 1) {
            if ("-d".equals(args[i])) {
                Settings.update(Settings.PATH, args[++i]);
            } else if ("-s".equals(args[i])) {
                String set = args[++i];
                if (set.equals(Settings.Mode.ABS.toString())) {
                    Settings.changeDist(Settings.Mode.ABS);
                    Settings.load();
                } else if (set.equals(Settings.Mode.REL.toString())) {
                    Settings.changeDist(Settings.Mode.REL);
                    Settings.load();
                }
            }
            i++;
        }
        // Version.main(args);
        MainApplication.main(args);
        Settings.save();
    }
}
