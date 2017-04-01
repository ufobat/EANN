package org.eann.sim.ui;

import org.eann.sim.configuration.World;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 * Created by martin on 01.04.17.
 */
public class WorldConfigTableModel extends AbstractTableModel {

    private final World world;
    private final ConfigRowValues[] rowValues;

    WorldConfigTableModel(final World world) {
        this.world = world;
        this.rowValues = new ConfigRowValues[] {
                new ConfigRowValues("Map Length", "The length of the world measured in titles") {
                    @Override
                    public String getValue() {
                        return Integer.toString( WorldConfigTableModel.this.world.getLength() );
                    }
                    @Override
                    public void setValue(Object o) {
                        WorldConfigTableModel.this.world.setLength(Integer.parseInt((String) o));
                    }
                },
                new ConfigRowValues("Map Width", "The width of the world measured in titles") {
                    @Override
                    public String getValue() {
                        return Integer.toString( WorldConfigTableModel.this.world.getWidth() );
                    }
                    @Override
                    public void setValue(Object o) {
                        WorldConfigTableModel.this.world.setWidth(Integer.parseInt((String) o));
                    }
                },
                new ConfigRowValues("Tile Size","The size of a square tile") {
                    @Override
                    public String getValue() {
                        return Integer.toString( WorldConfigTableModel.this.world.getTileSize() );
                    }
                    @Override
                    public void setValue(Object o) {
                        WorldConfigTableModel.this.world.setTileSize(Integer.parseInt((String) o));
                    }

                }
        };
    }

    abstract class ConfigRowValues {
        String name;
        String description;

        ConfigRowValues(final String name, final String description) {
            this.name = name;
            this.description = description;
        }

        public abstract String getValue();
        public abstract void setValue(Object o);
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
        return 3;
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
