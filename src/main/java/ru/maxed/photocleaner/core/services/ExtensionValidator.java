package ru.maxed.photocleaner.core.services;

import ru.maxed.photocleaner.core.exeptions.TestExeption;

import java.util.Locale;
/*
Проверка доступности введенного пользователем расширения
 */
public class ExtensionValidator {
    private ExtensionValidator(){
        throw new IllegalStateException("Utility class");
    }
    public static void validate(String str1,String str2) throws TestExeption {
        if (str1.equals("") || str2.equals("")) {
            throw new TestExeption("Пожалуйста, введите необходимые расширения файлов.");
        }
        if (str1.toLowerCase(Locale.ROOT).equals(str2.toLowerCase(Locale.ROOT))) {
            throw new TestExeption("Расширение файлов не может быть одинаковым");
        }
        if (Math.max(str1.lastIndexOf("."),str2.lastIndexOf("."))>0) {
            throw new TestExeption("Неправильный формат расширения");
        }
    }
}
