package org.eann.sim.simulation;

import org.eann.sim.configuration.Config;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by martin on 17.03.17.
 */
public class WorldFactoryTest {

    @Test
    public void buildWorld() {
        Config config = new Config();
        WorldFactory worldFactory = new WorldFactory(config.getWorld());
        World world = worldFactory.buildWorld();
        Tile[][] tiles = world.getTiles();
        for(int x = 0; x < tiles.length - 1; x++) {
            for(int y = 0; y < tiles[x].length - 1; y++) {
                Tile t = tiles[x][y];
                assertNotNull("tile at (" + x + "," + y + ") is defined", t);
                assertTrue("height on tile is <= 100 hight: " + t.getHeight(), t.getHeight() <= 100f);
                assertTrue("height on tile is >= -100 hight: " + t.getHeight(), t.getHeight() >= -100f);
            }
        }
    }
}
