package org.eann.sim.simulation;

import org.eann.sim.configuration.Config;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by martin on 17.03.17.
 */
public class MapFactoryTest {

    @Test
    public void buildWorld() {
        final Config config = new Config();
        final MapFactory mapFactory = new MapFactory(config.getWorld());
        final World world = mapFactory.buildWorld();
        final Map map = world.getMap();
        final Tile[][] tiles = map.getTiles();
        for(int x = 0; x < tiles.length - 1; x++) {
            for(int y = 0; y < tiles[x].length - 1; y++) {
                final Tile t = tiles[x][y];
                assertNotNull("tile at (" + x + "," + y + ") is defined", t);
                assertTrue("height on tile is <= 100 hight: " + t.getHeight(), t.getHeight() <= 100f);
                assertTrue("height on tile is >= -100 hight: " + t.getHeight(), t.getHeight() >= -100f);
            }
        }
    }
}
