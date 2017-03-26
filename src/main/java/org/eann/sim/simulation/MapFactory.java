package org.eann.sim.simulation;

import org.eann.sim.simulation.mapgeneration.PerlinNoiseFactory;

/**
 * Created by martin on 17.03.17.
 */
public class MapFactory {
    private final org.eann.sim.configuration.World worldConfig;

    public MapFactory(final org.eann.sim.configuration.World worldConfig) {
        this.worldConfig = worldConfig;
    }

    public World buildWorld() {
        final PerlinNoiseFactory heightFactory = new PerlinNoiseFactory();
        heightFactory.randomize();

        final double[][] height = heightFactory.buildHeightMap(this.worldConfig.getWidth(),
                this.worldConfig.getLength());

        final Map map = new Map(height, this.worldConfig.getTileSize());
        return new World(map);
    }
}
