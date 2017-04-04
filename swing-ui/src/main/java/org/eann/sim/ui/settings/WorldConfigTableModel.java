package org.eann.sim.ui.settings;

import org.eann.sim.configuration.WorldSettings;

/**
 * Created by martin on 01.04.17.
 */
public class WorldConfigTableModel extends AbstractConfigTableModel {
    private final WorldSettings worldSettings;

    WorldConfigTableModel(final WorldSettings worldSettings) {
        super();
        this.worldSettings = worldSettings;
        this.rowValues = new AbstractConfigRowValues[] {
                new AbstractConfigRowValues("Map Length", "The length of the worldSettings measured in titles") {
                    @Override
                    public String getValue() {
                        return Integer.toString( WorldConfigTableModel.this.worldSettings.getLength() );
                    }
                    @Override
                    public void setValue(final Object o) {
                        WorldConfigTableModel.this.worldSettings.setLength(Integer.parseInt((String) o));
                    }
                },
                new AbstractConfigRowValues("Map Width", "The width of the worldSettings measured in titles") {
                    @Override
                    public String getValue() {
                        return Integer.toString( WorldConfigTableModel.this.worldSettings.getWidth() );
                    }
                    @Override
                    public void setValue(final Object o) {
                        WorldConfigTableModel.this.worldSettings.setWidth(Integer.parseInt((String) o));
                    }
                },
                new AbstractConfigRowValues("Tile Size","The size of a square tile") {
                    @Override
                    public String getValue() {
                        return Integer.toString( WorldConfigTableModel.this.worldSettings.getTileSize() );
                    }
                    @Override
                    public void setValue(final Object o) {
                        WorldConfigTableModel.this.worldSettings.setTileSize(Integer.parseInt((String) o));
                    }

                }
        };
    }
}
