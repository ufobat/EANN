package org.eann.sim.simulation;

import org.eann.sim.configuration.Config;

/**
 * Created by martin on 16.03.17.
 */
public class Simulation extends Thread {
    private World world;
    private volatile boolean abortSimulation = false;
    private volatile boolean pauseSimulation = true;

    public synchronized void setPauseSimulation(boolean pauseSimulation) {
        this.pauseSimulation = pauseSimulation;
    }

    public Simulation() {
        this.world = new MapFactory(new Config().getWorld()).buildWorld();
        this.world.spawnRandomCreature();
        super.start();
    }

    public synchronized World getWorld() {
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
                    e.printStackTrace();
                }
            }
        }
    }
}
