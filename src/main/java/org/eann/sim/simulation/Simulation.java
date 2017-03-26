package org.eann.sim.simulation;

import org.eann.sim.configuration.Config;

/**
 * Created by martin on 16.03.17.
 */
public class Simulation extends Thread {
    final private World world;
    private volatile boolean abortSimulation;
    private volatile boolean pauseSimulation;

    public void setPauseSimulation(boolean pauseSimulation) {
        this.pauseSimulation = pauseSimulation;
    }

    public Simulation() {
        super();
        this.abortSimulation = false;
        this.pauseSimulation = true;
        this.world = new MapFactory(new Config().getWorld()).buildWorld();
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
                    // FIXME logger
                    e.printStackTrace();
                }
            }
        }
    }
}
