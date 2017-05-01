package org.eann.sim.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by martin on 01.05.17.
 */
public class StatList {
    private Object min;
    private Object max;
    private Object median;
    private Object mean;

    public StatList() {
        this.max = "";
        this.min = "";
        this.median = "";
        this.mean = "";
    }

    public void updateData(final List<Integer> list) {
        if (! list.isEmpty()) {
            final ArrayList<Integer> copy = new ArrayList(list);
            final int size = list.size();

            Collections.sort(copy);
            this.min = copy.get(0);
            this.max = copy.get(size - 1);
            this.median = copy.get(size / 2);
            double sum = 0;
            for (final Number elem : copy) {
                sum += elem.doubleValue();
            }
            this.mean = sum / size;
        }
    }

    public Object getMin() {
        return this.min;
    }

    public Object getMax() {
        return this.max;
    }

    public Object getMedian() {
        return this.median;
    }

    public Object getMean() {
        return this.mean;
    }
}
