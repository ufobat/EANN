package org.eann.sim.simulation.neuronalnet;

import java.util.Random;

/**
 * Created by martin on 24.03.17.
 */
public class RandomWeightGenerator {
    private Random randomGenerator;

    RandomWeightGenerator() {
        this.randomGenerator = new Random();
    }

    public double nextRandomWeight() {
        // 0 .. 1
        double random = this.randomGenerator.nextDouble();
        // -.5 .. 0.5
        random = random - 0.5;
        // -1.25 .. -0,75  oder  0.75 .. 1.25
        random = random < 0 ? random - 0.75 : random + 0.75;
        return random;
    }
}
