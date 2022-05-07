package ru.maxed.photocleaner.ui.desktop.services;

import javafx.scene.control.Button;

public class Counter implements Countable {

    Button handledButton;
    int value;

    public Counter(Button handledButton) {
        value = 0;
        this.handledButton = handledButton;
        handledButton.setDisable(true);
    }

    /**
     * Увеличение счётчика с включением кноки при  значении > 0.
     */
    public void add() {
        if (value == 0) {
            handledButton.setDisable(false);
        }
        value++;
    }

    /**
     * Уменьшение счётчика с выключением кнопки при значении 0.
     */
    public void sub() {
        value--;
        if (value == 0) {
            handledButton.setDisable(true);
        }
    }

}
