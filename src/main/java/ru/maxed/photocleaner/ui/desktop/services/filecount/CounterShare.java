package ru.maxed.photocleaner.ui.desktop.services.filecount;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CounterShare {
    List<Countable> counts = new ArrayList<>();

    public void add(Countable... c) {
        counts.addAll(Arrays.asList(c));
    }

    public void update(boolean bool) {
        for (Countable counter : counts) {
            if (bool) {
                counter.add();
            } else {
                counter.sub();
            }
        }
    }
}
