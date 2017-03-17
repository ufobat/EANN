package org.eann.sim.simulation;

import org.eann.sim.configuration.Config;
import org.junit.Test;

/**
 * Created by martin on 17.03.17.
 */
public class WorldFactoryTest {

    @Test
    public void buildWorld() throws AddTitleException {
        Config config = new Config();
        WorldFactory worldFactory = new WorldFactory(config.getWorld());
        World world = worldFactory.buildWorld();

    }
}
