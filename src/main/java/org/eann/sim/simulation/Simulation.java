package org.eann.sim.simulation;

import org.eann.sim.configuration.Config;

/**
 * Created by martin on 16.03.17.
 */
public class Simulation {
    private World world;

    private boolean continueSimulation = false;

    public Simulation() {
        this.world = new MapFactory(new Config().getWorld()).buildWorld();
        Creature c = new Creature(10,10);
        this.world.addCreature(c);
    }

    public World getWorld() {
        return this.world;
    }

    public void run(){
        while (this.continueSimulation) {
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
