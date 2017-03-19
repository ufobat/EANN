package org.eann.sim.ui;

import org.eann.sim.configuration.Config;
import org.eann.sim.configuration.ConfigFactory;
import org.eann.sim.simulation.Map;
import org.eann.sim.simulation.MapFactory;

/**
 * Created by martin on 16.03.17.
 */
public class Cli {

    public static void main(String argv[]) {
        ConfigFactory configfactory = new ConfigFactory();
        Config config = argv.length  > 1 ?
                configfactory.buildConfiguration() :
                configfactory.buildConfiguration(argv[0]);

        MapFactory mapFactory = new MapFactory(config.getWorld());
        Map map = mapFactory.buildWorld();

    }
}
