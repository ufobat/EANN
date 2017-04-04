package org.eann.sim.simulation;

import org.eann.sim.configuration.CreatureSettings;
import org.eann.sim.configuration.WorldSettings;
import org.eann.sim.simulation.mapgeneration.PerlinNoiseFactory;

/**
 * Created by martin on 17.03.17.
 */
public class WorldFactory {
    private final WorldSettings worldSettings;

    public WorldFactory(final WorldSettings worldSettings) {
        this.worldSettings = worldSettings;
    }

    public World buildWorld() {
        final PerlinNoiseFactory heightFactory = new PerlinNoiseFactory();
        heightFactory.randomize();

        final double[][] height = heightFactory.buildHeightMap(this.worldSettings.getWidth(),
                this.worldSettings.getLength());

        final Map map = new Map(height, this.worldSettings.getTileSize());
        final CreatureSettings creatureSettings = this.worldSettings.getCreatureSettings();
        return new World(map, creatureSettings);
    }
}
