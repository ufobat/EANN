package org.eann.sim.simulation;

import org.eann.sim.configuration.Config;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by martin on 17.03.17.
 */
public class MapFactoryTest {

    @Test
    @SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.JUnitTestContainsTooManyAsserts"})
    public void buildWorld() {
        final Config config = new Config();
        final MapFactory mapFactory = new MapFactory(config.getWorld());
        final World world = mapFactory.buildWorld();
        final Map map = world.getMap();
        final Tile[][] tiles = map.getTiles();
        for(int x = 0; x < tiles.length - 1; x++) {
            for(int y = 0; y < tiles[x].length - 1; y++) {
                final Tile t = tiles[x][y];
                assertNotNull(t, "tile at (" + x + "," + y + ") is defined");
                assertThat(t.getHeight()).isBetween(-100.0, 100.0);
            }
        }
    }
}
