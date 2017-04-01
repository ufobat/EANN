package org.eann.sim.ui.settings;

import org.eann.sim.configuration.World;

/**
 * Created by martin on 01.04.17.
 */
public class WorldConfigTableModel extends AbstractConfigTableModel {
    private final World world;

    WorldConfigTableModel(final World world) {
        super();
        this.world = world;
        this.rowValues = new AbstractConfigRowValues[] {
                new AbstractConfigRowValues("Map Length", "The length of the world measured in titles") {
                    @Override
                    public String getValue() {
                        return Integer.toString( WorldConfigTableModel.this.world.getLength() );
                    }
                    @Override
                    public void setValue(final Object o) {
                        WorldConfigTableModel.this.world.setLength(Integer.parseInt((String) o));
                    }
                },
                new AbstractConfigRowValues("Map Width", "The width of the world measured in titles") {
                    @Override
                    public String getValue() {
                        return Integer.toString( WorldConfigTableModel.this.world.getWidth() );
                    }
                    @Override
                    public void setValue(final Object o) {
                        WorldConfigTableModel.this.world.setWidth(Integer.parseInt((String) o));
                    }
                },
                new AbstractConfigRowValues("Tile Size","The size of a square tile") {
                    @Override
                    public String getValue() {
                        return Integer.toString( WorldConfigTableModel.this.world.getTileSize() );
                    }
                    @Override
                    public void setValue(final Object o) {
                        WorldConfigTableModel.this.world.setTileSize(Integer.parseInt((String) o));
                    }

                }
        };
    }
}
