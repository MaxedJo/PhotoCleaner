package ru.maxed.photocleaner.core.services;

import java.util.Locale;

public final class ExtensionCorrecter {
    private ExtensionCorrecter() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Приведение введеного расширения к стандартному виду.
     *
     * @param extension Расширение
     * @return Расширение в стандартном виде
     */
    public static String correct(final String extension) {
        String correctExtension = extension.toLowerCase(Locale.ROOT);
        if (!extension.startsWith(".")) {
            correctExtension = "." + extension;
        }
        return correctExtension;
    }
}
