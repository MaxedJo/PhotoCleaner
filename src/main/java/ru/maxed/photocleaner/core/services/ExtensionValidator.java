package ru.maxed.photocleaner.core.services;

import ru.maxed.photocleaner.LangLib;
import ru.maxed.photocleaner.core.exeptions.TestException;

import java.util.Locale;

/**
 * Утилитый класс проверки правильности расширений.
 */
public final class ExtensionValidator {
    /**
     * Закрывающий конструктор.
     */
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
                    LangLib.NEED_EXTENSIONS_ERROR.toString()
            );
        }
        if (str1.toLowerCase(Locale.ROOT)
                .equals(str2.toLowerCase(Locale.ROOT))) {
            throw new TestException(
                    LangLib.SAME_EXTENSIONS_ERROR.toString()
            );
        }
        if (Math.max(str1.lastIndexOf("."), str2.lastIndexOf(".")) > 0) {
            throw new TestException(LangLib.WRONG_EXTENSIONS_ERROR.toString());
        }
    }
}
