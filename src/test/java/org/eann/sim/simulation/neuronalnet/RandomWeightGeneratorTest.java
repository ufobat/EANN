package org.eann.sim.simulation.neuronalnet;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;


/**
 * Created by martin on 24.03.17.
 */
public class RandomWeightGeneratorTest {

    @Test
    @SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.JUnitTestContainsTooManyAsserts"})
    public void checkALotOfWeights() {
        final RandomWeightGenerator rwg = new RandomWeightGenerator(new Random());
        for (int i = 0; i < 1000000; i++) {
            final double weight = rwg.nextRandomWeight();
            if (weight < 0) {
                assertTrue("negative random weight smaller then -0.75 -> " + weight, weight <= -0.75);
                assertTrue("negative random weight bigger then -1.25 -> " + weight, weight > -1.25);
            } else {
                assertTrue("positive random weight bigger then 0.75 -> " + weight, weight >= 0.75);
                assertTrue("positive random weight smaller then 1.25 -> " + weight, weight < 1.25);
            }
        }
    }
}
