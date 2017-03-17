package org.eann.sim.ui;

import org.eann.sim.simulation.Simulation;

/**
 * Created by martin on 16.03.17.
 */
public class Cli {

    public static void main(String argv[]) {
        Simulation sim = new Simulation();
        sim.run();
    }
}
