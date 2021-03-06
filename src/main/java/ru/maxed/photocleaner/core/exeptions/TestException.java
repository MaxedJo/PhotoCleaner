package ru.maxed.photocleaner.core.exeptions;

/**
 * Пользовательский класс ошибки.
 */
public class TestException extends Exception {
    /**
     * Конструктор исключения.
     *
     * @param message Сообщение ошибки
     */
    public TestException(final String message) {
        super(message);
    }
}
