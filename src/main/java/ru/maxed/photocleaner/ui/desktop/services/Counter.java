package ru.maxed.photocleaner.ui.desktop.services;

import javafx.scene.control.Button;

/**
 * Счётчик файлов, переключающий кнопку.
 */
public class Counter {
    /**
     * Кнопка переключаемая счётчиком.
     */
    private final Button button;
    /**
     * Значание счётчика.
     */
    private int value;

    /**
     * Создание счётчика контролирующего кнопку.
     *
     * @param handledButton контроллируемая кнопка
     */
    public Counter(final Button handledButton) {
        value = 0;
        this.button = handledButton;
        handledButton.setDisable(true);
    }

    /**
     * Увеличение счётчика с включением кноки при  значении > 0.
     */
    public void add() {
        if (value == 0) {
            button.setDisable(false);
        }
        value++;
    }

    /**
     * Уменьшение счётчика с выключением кнопки при значении 0.
     */
    public void sub() {
        value--;
        if (value == 0) {
            button.setDisable(true);
        }
    }
}
