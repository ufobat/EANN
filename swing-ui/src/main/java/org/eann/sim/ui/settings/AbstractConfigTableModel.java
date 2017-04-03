package org.eann.sim.ui.settings;

import javax.swing.table.AbstractTableModel;

/**
 * Created by martin on 02.04.17.
 */
public abstract class AbstractConfigTableModel extends AbstractTableModel {
    protected AbstractConfigRowValues[] rowValues;

    abstract class AbstractConfigRowValues {
        private final String name;
        private final String description;

        AbstractConfigRowValues(final String name, final String description) {
            this.name = name;
            this.description = description;
        }

        public abstract String getValue();
        public abstract void setValue(final Object o);
        public String getName() {return this.name; }
        public String getDescription() {return this.description; }

        public String getAt(final int col) {
            String value;
            switch (col) {
                case 0:
                    value = this.getName();
                    break;
                case 1:
                    value = this.getValue();
                    break;
                default:
                    value = this.getDescription();
                    break;
            }
            return value;
        }
    }

    @Override
    public String getColumnName(final int i) {
        String name;
        switch (i) {
            case 0:
                name = "Attribute";
                break;
            case 1:
                name = "Value";
                break;
            default:
                name = "Description";
                break;
        }
        return name;
    }

    @Override
    public int getRowCount() {
        return this.rowValues.length;
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(final int row, final int col) {
        return this.rowValues[row].getAt(col);
    }

    @Override
    public boolean isCellEditable(final int row, final int col) {
        return col == 1;
    }

    @Override
    public void setValueAt(final Object o, final int row, final int col) {
        super.setValueAt(o, row, col);
        this.rowValues[row].setValue(o);
        this.fireTableCellUpdated(row, col);
    }
}
