package org.eann.sim.simulation.neuronalnet;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by martin on 24.03.17.
 */
public class NeuronalNetworkTest {

    @Test()
    public void toSmallInputVector() {
        final NeuronalNetworkFactory factory = new NeuronalNetworkFactory();
        final NeuronalNetwork brain = factory.buildBrain(2,1);

        double[] iv = new double[] {1};
        assertThrows(IllegalArgumentException.class, () -> { brain.think(iv); });
    }

    @Test()
    public void toLargeInputVector() {
        final NeuronalNetworkFactory factory = new NeuronalNetworkFactory();
        final NeuronalNetwork brain = factory.buildBrain(2,1);

        double[] iv = new double[] {1,2,3};
        assertThrows(IllegalArgumentException.class, () -> { brain.think(iv); });
    }

    @Test()
    public void think() {
        final NeuronalNetworkFactory factory = new NeuronalNetworkFactory();
        final NeuronalNetwork brain = factory.buildBrain(2,1);

        final double[] iv = new double[] {1,2};
        final double[] ov = brain.think(iv);
        assertEquals(1, ov.length);
        Assertions.assertThat(ov[0]).isNotNaN();
    }
}
