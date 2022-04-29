package ru.maxed.photocleaner.ui.desktop;

import javafx.scene.control.Button;

public class Counter {
    private final Button button;
    private int value;

    public Counter(Button handledButton) {
        value = 0;
        this.button = handledButton;
        handledButton.setDisable(true);
    }

    public void add() {
        if (value == 0) button.setDisable(false);
        value++;
    }

    public void sub() {
        value--;
        if (value == 0) button.setDisable(true);
    }
}
