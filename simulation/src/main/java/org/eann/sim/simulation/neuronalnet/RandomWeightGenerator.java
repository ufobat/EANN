package org.eann.sim.simulation.neuronalnet;

import java.util.Random;

/**
 * Created by martin on 24.03.17.
 */
public class RandomWeightGenerator {
    private final Random randomGenerator;

    RandomWeightGenerator(final Random randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    /**
     * Generates a random weight for a NeuronalNetwork. Random weights are between -1.25 and -0.75
     * or 0.75 - 1.25
     * @retun double between -1.25 and -0.75 or 0.75 - 1.25
     */

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
