package ru.maxed.photocleaner.core.services;

import ru.maxed.photocleaner.ui.desktop.ErrorStage;

import java.util.Locale;

public class ExtensionValidator {
    private ExtensionValidator(){
        throw new IllegalStateException("Utility class");
    }
    public static boolean validate(String str1,String str2) {
        if (str1.equals("") || str2.equals("")) {
            new ErrorStage("Пожалуйста, введите необходимые расширения файлов.");
            return true;
        }
        if (str1.toLowerCase(Locale.ROOT).equals(str2.toLowerCase(Locale.ROOT))) {
            new ErrorStage("Расширение файлов не может быть одинаковым");
            return true;
        }
        return false;
    }
}
