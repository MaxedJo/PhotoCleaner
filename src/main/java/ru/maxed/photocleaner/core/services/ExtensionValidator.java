package ru.maxed.photocleaner.core.services;

import ru.maxed.photocleaner.core.exeptions.TestException;

import java.util.Locale;

/*
Проверка доступности введенного пользователем расширения
 */
public final class ExtensionValidator {
    private ExtensionValidator() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Проверяет пару расширений на корректность.
     *
     * @param str1 Первое расширение
     * @param str2 Второе расширение
     * @throws TestException Расширения ошибочны
     */
    public static void validate(
            final String str1,
            final String str2
    ) throws TestException {
        if (str1.equals("") || str2.equals("")) {
            throw new TestException(
                    "Пожалуйста, введите необходимые расширения файлов."
            );
        }
        if (str1.toLowerCase(Locale.ROOT)
                .equals(str2.toLowerCase(Locale.ROOT))) {
            throw new TestException(
                    "Расширение файлов не может быть одинаковым"
            );
        }
        if (Math.max(str1.lastIndexOf("."), str2.lastIndexOf(".")) > 0) {
            throw new TestException("Неправильный формат расширения");
        }
    }
}
