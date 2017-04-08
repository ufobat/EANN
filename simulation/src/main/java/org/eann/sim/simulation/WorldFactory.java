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
        final PerlinNoiseFactory heightFactory = new PerlinNoiseFactory();
        heightFactory.randomize();
        final WorldSettings worldSettings = this.config.getWorldSettings();

        final double[][] height = heightFactory.buildHeightMap(worldSettings.getWidth(),
                worldSettings.getLength());


        final Map map = new Map(height, this.config);
        return new World(map, this.config);
    }
}
