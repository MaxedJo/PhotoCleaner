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
        while (i < args.length) {
            if ("-d".equals(args[i])) {
                Settings.update(Settings.PATH, args[++i]);
            }
            i++;
        }
        MainApplication.main(args);
        Settings.save();
    }
}
