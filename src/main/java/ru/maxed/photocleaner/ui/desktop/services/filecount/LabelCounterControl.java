package ru.maxed.photocleaner.ui.desktop.services.filecount;

import javafx.scene.control.Label;

public class LabelCounterControl implements Countable {
    private final Label label;
    private int current;
    private int max;

    public LabelCounterControl(Label label) {
        max = 0;
        current = 0;
        label.setText("Выделено " + current + " из " + max);
        this.label = label;
    }

    private void updateText() {
        label.setText("Выделено " + current + " из " + max);
    }

    public void sub() {
        current--;
        updateText();
    }

    public void add() {
        current++;
        updateText();
    }

    public void subMax() {
        max--;
        updateText();
    }

    public void addMax() {
        max++;
        updateText();
    }
}
