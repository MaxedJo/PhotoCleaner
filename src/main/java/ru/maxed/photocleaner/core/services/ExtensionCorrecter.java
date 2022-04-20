package ru.maxed.photocleaner.core.services;

import java.util.Locale;
/*
Корректировка введённого пользователем расширения
 */
public class ExtensionCorrecter {
    private ExtensionCorrecter(){
        throw new IllegalStateException("Utility class");
    }
    public static String correct(String extension){
        extension = extension.toLowerCase(Locale.ROOT);
        if (!extension.startsWith(".")) extension ="."+extension;
        return extension;
    }
}
