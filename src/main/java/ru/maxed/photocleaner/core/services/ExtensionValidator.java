package ru.maxed.photocleaner.core.services;

import ru.maxed.photocleaner.ui.desktop.ErrorStage;

import java.util.Locale;
/*
Проверка доступности введенного пользователем расширения
 */
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
        if (Math.max(str1.lastIndexOf("."),str2.lastIndexOf("."))>0) {
            new ErrorStage("Неправильный формат расширения");
            return true;
        }
        return false;
    }
}
