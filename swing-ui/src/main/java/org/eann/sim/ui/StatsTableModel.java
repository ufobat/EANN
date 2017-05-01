package org.eann.sim.ui;

import org.eann.sim.simulation.dataexchange.Snapshot;
import org.eann.sim.simulation.dataexchange.StatisticsBean;
import javax.swing.table.AbstractTableModel;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by martin on 02.04.17.
 */
public class StatsTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 4665187252420804709L;
    private final String[] names;
    private Map<Integer, Object> cache;

    @SuppressWarnings("PMD.ConstructorShouldDoInitialization")
    public StatsTableModel() {
        super();
        this.names = new String[] {
                "Date",
                "No of Creatures",
                "No of Spawns",

                "Min Age at Death",
                "Max Age at Death",
                "Mean Age at Death",
                "Median Age at Death",

                "Min No of Children",
                "Max No of Children",
                "Mean No of Children",
                "Median No of Children",
        };
    }

    @Override
    public int getRowCount() {
        return this.names.length;
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(final int row, final int col) {
         return col == 0
                ? this.names[row]
                : this.cache == null
                    ? ""
                    : this.cache.get(Integer.valueOf(row));
    }

    public void setSnapshot(final Snapshot snapshot) {
        this.cache = new HashMap<>();
        final StatisticsBean stats = snapshot.getStats();
        this.cache.put(Integer.valueOf(0), stats.getDate());
        this.cache.put(Integer.valueOf(1), stats.getNoOfCreatures());
        this.cache.put(Integer.valueOf(2), stats.getSpawns());

        final StatList ages = new StatList();
        ages.updateData(stats.getAges());
        this.cache.put(Integer.valueOf(3), ages.getMin());
        this.cache.put(Integer.valueOf(4), ages.getMax());
        this.cache.put(Integer.valueOf(5), ages.getMean());
        this.cache.put(Integer.valueOf(6), ages.getMedian());

        final StatList children = new StatList();
        children.updateData(stats.getNoOfChildren());
        this.cache.put(Integer.valueOf(7), children.getMin());
        this.cache.put(Integer.valueOf(8), children.getMax());
        this.cache.put(Integer.valueOf(9), children.getMean());
        this.cache.put(Integer.valueOf(10), children.getMedian());
    }
}
