// This is an open source non-commercial project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
package ru.maxed.photocleaner;

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
    public static void main(final String[] args) {
        MainApplication.main(args);
    }
}
