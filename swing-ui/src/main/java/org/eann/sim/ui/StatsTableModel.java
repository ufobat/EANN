package org.eann.sim.ui;

import org.eann.sim.simulation.dataexchange.Snapshot;
import org.eann.sim.simulation.dataexchange.StatisticsBean;
import javax.swing.table.AbstractTableModel;

/**
 * Created by martin on 02.04.17.
 */
public class StatsTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 4665187252420804709L;
    private final String[] names;
    private Snapshot snapshot;

    @SuppressWarnings("PMD.ConstructorShouldDoInitialization")
    public StatsTableModel() {
        super();
        this.names = new String[] {
                "Date",
                "No of Creatures",
                "No of Births",
                "Avg Age at Death",
                "Avg No of Children",
                "Todo"
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
        Object value;
        if (col == 0) {
            value = this.names[row];
        } else {
            value = this.getValueFor(row);
        }
        return value;
    }

    private Object getValueFor(final int row) {
        Object value;

        if (this.snapshot == null) {
            value = "";
        } else {
            final StatisticsBean stats = this.snapshot.getStats();
            switch (row) {
                case 0:
                    value = stats.getDate();
                    break;
                case 1:
                    value = stats.getNoOfCreatures();
                    break;
                case 2:
                    value = stats.getSpawns();
                    break;
                case 3:
                    value = stats.getAvgAgeAtDeath();
                    break;
                case 4:
                    value = stats.getAvgNoOfChildren();
                    break;
                default:
                    value = "";
                    break;
            }
        }
        return value;
    }

    public void setSnapshot(final Snapshot snapshot) {
        this.snapshot = snapshot;
    }
}
