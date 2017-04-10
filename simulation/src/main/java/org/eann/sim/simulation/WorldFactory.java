package org.eann.sim.simulation;

import org.eann.sim.configuration.Config;
import org.eann.sim.configuration.WorldSettings;
import org.eann.sim.simulation.mapgeneration.PerlinNoiseFactory;

/**
 * Created by martin on 17.03.17.
 */
public class WorldFactory {
    private final Config config;

    public WorldFactory(final Config config) {
        this.config = config;
    }

    public World buildWorld() {
        final Map map = this.buildMap();
        return new World(map, this.config);
    }

    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public Map buildMap() {
        final WorldSettings worldSettings = this.config.getWorldSettings();
        final PerlinNoiseFactory heightFactory = new PerlinNoiseFactory();
        heightFactory.randomize();

        final double[][] heights = heightFactory.buildHeightMap(worldSettings.getWidth(),
                worldSettings.getLength());

        final Tile[][] tiles = new Tile[heights.length][heights[0].length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = new Tile(heights[i][j], i, j);
            }
        }

        return new Map(tiles, worldSettings.getTileSize());
    }
}
