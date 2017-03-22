package org.eann.sim.simulation;

import org.eann.sim.configuration.Config;

/**
 * Created by martin on 16.03.17.
 */
public class Simulation extends Thread {
    private World world;
    private boolean pauseSimulation = true;
    private boolean abortSimulation = false;

    public synchronized void setPauseSimulation(boolean pauseSimulation) {
        this.pauseSimulation = pauseSimulation;
    }

    public Simulation() {
        this.world = new MapFactory(new Config().getWorld()).buildWorld();
        Creature c = new Creature(10,10);
        this.world.addCreature(c);
        System.out.println("starting thread");
        super.start();
    }

    public synchronized World getWorld() {
        return this.world;
    }


    @Override
    public void run() {
        while (! this.abortSimulation) {
            System.out.println("next step");
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
