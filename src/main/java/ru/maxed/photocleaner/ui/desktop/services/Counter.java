package ru.maxed.photocleaner.ui.desktop.services;

import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ru.maxed.photocleaner.LangLib;
import ru.maxed.photocleaner.ListKey;

import java.text.MessageFormat;

public class Counter {

    IntegerProperty originCounter = new SimpleIntegerProperty();
    IntegerProperty processedCounter = new SimpleIntegerProperty();
    IntegerProperty processedMax = new SimpleIntegerProperty();
    IntegerProperty originMax = new SimpleIntegerProperty();
    StringBinding processedLabelText = new StringBinding() {
        {
            super.bind(processedCounter, processedMax);
        }

        @Override
        protected String computeValue() {
            return MessageFormat.format(LangLib.COUNTER_LABEL.toString(), processedCounter.get(), processedMax.get());
        }
    };
    StringBinding originLabelText = new StringBinding() {
        {
            super.bind(originCounter, originMax);
        }

        @Override
        protected String computeValue() {
            return MessageFormat.format(LangLib.COUNTER_LABEL.toString(), originCounter.get(), originMax.get());
        }
    };
    IntegerBinding mainCounter = new IntegerBinding() {
        {
            super.bind(originCounter, processedCounter);
        }

        @Override
        protected int computeValue() {
            return originCounter.get() + processedCounter.get();
        }
    };

    public Counter(Button handledButton, Label originLabel, Label processedLabel) {
        originLabelText.addListener((observable, oldValue, newValue) -> originLabel.setText(newValue));
        processedLabelText.addListener((observable, oldValue, newValue) -> processedLabel.setText(newValue));
        mainCounter.addListener((observable, oldValue, newValue) -> handledButton.setDisable(newValue.equals(0)));
        handledButton.setDisable(true);
    }

    public void reInit() {
        processedCounter.set(0);
        originCounter.set(0);
    }

    public void change(Boolean rise, ListKey key) {
        int value = Boolean.TRUE.equals(rise) ? 1 : -1;
        if (key.equals(ListKey.ORIGIN)) {
            originCounter.set(originCounter.get() + value);
        } else if (key.equals(ListKey.PROCESSED)) {
            processedCounter.set(processedCounter.get() + value);
        }
    }

    public void changeMax(boolean rise, ListKey key) {
        int value = Boolean.TRUE.equals(rise) ? 1 : -1;
        if (key.equals(ListKey.ORIGIN)) {
            originMax.set(originMax.get() + value);
        } else if (key.equals(ListKey.PROCESSED)) {
            processedMax.set(processedMax.get() + value);
        }
    }
}
