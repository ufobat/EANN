package org.eann.sim.simulation;

import org.eann.sim.simulation.mapgeneration.DiamondSquareFactory;
import org.eann.sim.simulation.mapgeneration.HeightArrayFactory;

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
        HeightArrayFactory heightArrayFactory = new DiamondSquareFactory(1.1);
        double[][] height = heightArrayFactory.buildHeightMap(this.worldConfiguration.getWidth(),
                this.worldConfiguration.getLength());

        World world = new World(height);
        return world;
    }

    private Tile buildTile(float height, int x, int y) {
        return new Tile(height, x, y);
    }
}
