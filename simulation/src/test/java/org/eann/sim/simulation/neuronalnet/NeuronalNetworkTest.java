package org.eann.sim.simulation.neuronalnet;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Created by martin on 24.03.17.
 */
public class NeuronalNetworkTest {

    /*
    private void dumpWeights(double[][] weights) {
        System.out.print("     ");
        for(int i = 0; i < weights.length; i++) {
            System.out.printf("  %2d:  ", i);
        }
        System.out.println();

        for (int src = 0; src < weights.length; src++) {
            System.out.printf("%2d: ", src);
            for (int dst = 0; dst < weights[src].length; dst++) {
                double value = weights[src][dst];
                if (Double.isNaN(value)) {
                    System.out.print("   NaN ");
                }
                else {
                    System.out.printf(" %+.2f ", value);
                }
            }
            System.out.println();
        }
    }
    */

    @Test
    @SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.JUnitTestContainsTooManyAsserts"})
    public void brainConnections() {
        final NeuronalNetwork nn = new NeuronalNetwork(3, 2, 3, 3);
        final double[] neurons = nn.getNeurons();
        final double[][] weights = nn.getConnectionWeights();
        // this.dumpWeights(weights);

        assertEquals(neurons.length, 12, "Got twelve Neurons");
        assertEquals(neurons[0], 1, "First neuron is 1");

        // input to hidden
        assertThat(weights[1][4]).isNotNaN();
        assertThat(weights[2][4]).isNotNaN();
        assertThat(weights[3][4]).isNotNaN();
        //...

        // hidden to output
        assertThat(weights[4][7]).isNotNaN();
        assertThat(weights[5][7]).isNotNaN();
        assertThat(weights[6][7]).isNotNaN();
        // ...

        // no connections From Bias to Bias
        assertThat(weights[0][0]).isNaN();

        for (int dst = 4; dst < neurons.length; dst++) {
            assertThat(weights[0][dst]).isNotNaN();
        }
    }

    @Test
    @SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.JUnitTestContainsTooManyAsserts"})
    public void brainThinking() {
        final NeuronalNetwork nn = new NeuronalNetwork(3, 2, 3, 3);
        final double[] input = new double[] { 1d, 1d, 1d };
        final double[] output = nn.think(input);

        assertSame(2, output.length, "got a 2 dimentionale output");
        for(final double d: output) {
            assertFalse(Double.isNaN(d), "output is a Number" );
        }

    }
}
