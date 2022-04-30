package ru.maxed.photocleaner;

/**
 * Главный класс программы.
 */
public final class App {
    private App() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Старт программы.
     *
     * @param args Стартовые аргументы
     */
    public static void main(final String[] args) {
        MainApplication.main(args);
    }
}
