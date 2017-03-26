package org.eann.sim.simulation;

import org.eann.sim.configuration.Config;

/**
 * Created by martin on 16.03.17.
 */
public class Simulation extends Thread {
    private World world;
    final private Config configuration;
    private volatile boolean abortSimulation;
    private volatile boolean pauseSimulation;

    public void setPauseSimulation(final boolean pauseSimulation) {
        this.pauseSimulation = pauseSimulation;
    }

    public Simulation(final Config configuration) {
        super();
        this.configuration = configuration;
        this.abortSimulation = false;
        this.pauseSimulation = true;
    }

    public void setup() {
        final MapFactory mapFactory = new MapFactory(this.configuration.getWorld());
        this.world = mapFactory.buildWorld();
    }

    public World getWorld() {
        return this.world;
    }

    @Override
    public void run() {
        while (! this.abortSimulation) {
            if (! this.pauseSimulation) {
                this.world.calculateNextStep();

                // sleep for next iteration
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                }
            }
        }
    }

}
