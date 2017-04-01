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

    WorldConfigTableModel(World world) {
        this.world = world;
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
        String value;
        switch (row) {
            case 0:
                switch(col) {
                    case 0: value = "Length";
                    break;
                    case 1: value = Integer.toString(this.world.getLength());
                    break;
                    default: value = "The length of the world measured in titles";
                }
                break;
            case 1:
                switch(col) {
                    case 0: value = "Width";
                        break;
                    case 1: value = Integer.toString(this.world.getWidth());
                        break;
                    default: value = "The width of the world measured in titles";
                }
                break;
            default:
                switch(col) {
                    case 0: value = "Tile Size";
                        break;
                    case 1: value = Integer.toString(this.world.getTileSize());
                        break;
                    default: value = "The size of a square tile";
                }
                break;

        }
        return value;
    }

    @Override
    public boolean isCellEditable(final int row, final int col) {
        return col == 1;
    }

    @Override
    public void setValueAt(final Object o, final int row, final int col) {
        super.setValueAt(o, row, col);
        this.fireTableCellUpdated(row, col);
        String data = (String) o;
        switch (row) {
            case 0:
                this.world.setLength(Integer.parseInt(data));
                break;
            case 1:
                this.world.setWidth(Integer.parseInt(data));
                break;
            default:
                this.world.setTileSize(Integer.parseInt(data));
                break;
        }

    }
}
