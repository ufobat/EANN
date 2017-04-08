package org.eann.sim.simulation;

import org.eann.sim.configuration.Config;

/**
 * Created by martin on 16.03.17.
 */
public class Simulation extends Thread {
    private World world;
    private Config configuration;
    private volatile boolean abortSimulation;
    private volatile boolean pauseSimulation;
    private long sleep;

    public void setPauseSimulation(final boolean pauseSimulation) {
        this.pauseSimulation = pauseSimulation;
    }

    public Simulation(final Config configuration) {
        super();
        this.configuration = configuration;
        this.abortSimulation = false;
        this.pauseSimulation = true;
        this.sleep = 250;
    }

    public void setup() {
        final WorldFactory worldFactory = new WorldFactory(this.configuration);
        this.world = worldFactory.buildWorld();
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
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public void setConfiguration(final Config configuration) {
        this.configuration = configuration;
    }

    public void setSleep(final long sleep) {
        this.sleep = sleep;
    }

    public void abort() {
        this.abortSimulation = true;
    }
}
