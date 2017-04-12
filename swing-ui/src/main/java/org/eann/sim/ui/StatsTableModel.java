package org.eann.sim.ui;

import org.eann.sim.simulation.Simulation;

import javax.swing.table.AbstractTableModel;

/**
 * Created by martin on 02.04.17.
 */
public class StatsTableModel extends AbstractTableModel {
    private final MainFrame mainframe;
    private final String[] names;

    @SuppressWarnings("PMD.ConstructorShouldDoInitialization")
    public StatsTableModel(final MainFrame simulation) {
        super();
        this.mainframe = simulation;
        this.names = new String[] {
                "Date",
                "No of Creatures",
                "No of Births",
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
        final Simulation simulation = this.mainframe.getSimulation();
        if (simulation == null) {
            value = "";
        } else {
            switch (row) {
                case 0:
                    value = simulation.getWorld().getDate();
                    break;
                case 1:
                    value = simulation.getWorld().getClonedCreatureStates().size();
                    break;
                case 2:
                    value = simulation.getWorld().getSpawns();
                    break;
                default:
                    value = "";
                    break;
            }
        }
        return value;
    }

}
