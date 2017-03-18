package org.eann.sim.simulation;

import org.eann.sim.simulation.mapgeneration.DiamondSquareFactory;
import org.eann.sim.simulation.mapgeneration.HeightArrayFactory;
import org.eann.sim.simulation.mapgeneration.PerlinNoiseFactory;

import java.util.Random;

/**
 * Created by martin on 17.03.17.
 */
public class WorldFactory {
    private final org.eann.sim.configuration.World worldConfiguration;

    public WorldFactory(org.eann.sim.configuration.World worldConfiguration) {
        this.worldConfiguration = worldConfiguration;
    }

    public World buildWorld() {
        PerlinNoiseFactory heightArrayFactory = new PerlinNoiseFactory();
        heightArrayFactory.randomize();

        double[][] height = heightArrayFactory.buildHeightMap(this.worldConfiguration.getWidth(),
                this.worldConfiguration.getLength());

        World world = new World(height, this.worldConfiguration.getTileSize());
        return world;
    }
}
