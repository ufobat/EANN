package org.eann.sim.simulation.neuronalnet;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;


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
                Assertions.assertThat(weight)
                        .as("Weight must be (-1.25, -0.75]")
                        .isGreaterThan(-1.25)
                        .isLessThanOrEqualTo(-0.75);
            } else {
                Assertions.assertThat(weight)
                        .as("Weight must be between [0.75, 1.25)")
                        .isGreaterThanOrEqualTo(0.75)
                        .isLessThan(1.25);
            }
        }
    }
}
