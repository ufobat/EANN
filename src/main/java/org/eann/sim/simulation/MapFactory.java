package org.eann.sim.simulation;

import org.eann.sim.simulation.mapgeneration.PerlinNoiseFactory;

/**
 * Created by martin on 17.03.17.
 */
public class MapFactory {
    private final org.eann.sim.configuration.World worldConfiguration;

    public MapFactory(org.eann.sim.configuration.World worldConfiguration) {
        this.worldConfiguration = worldConfiguration;
    }

    public World buildWorld() {
        PerlinNoiseFactory heightArrayFactory = new PerlinNoiseFactory();
        heightArrayFactory.randomize();

        double[][] height = heightArrayFactory.buildHeightMap(this.worldConfiguration.getWidth(),
                this.worldConfiguration.getLength());

        Map map = new Map(height, this.worldConfiguration.getTileSize());
        return new World(map);
    }
}
