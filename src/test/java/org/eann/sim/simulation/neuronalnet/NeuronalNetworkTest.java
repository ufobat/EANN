package org.eann.sim.simulation.neuronalnet;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Created by martin on 24.03.17.
 */
public class NeuronalNetworkTest {

    /*
    private void dumpWeights(double[][] weights) {
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
        final double[][] weights = nn.getConnectionWeights();
        // this.dumpWeights(weights);

        // input to hidden
        assertThat(weights[0][3]).isNaN();
        assertThat(weights[1][3]).isNaN();
        assertThat(weights[2][3]).isNaN();
        //...

        // hidden to output
        assertThat(weights[3][6]).isNaN();
        assertThat(weights[4][6]).isNaN();
        assertThat(weights[5][6]).isNaN();
        // ...

        // no connections
        assertThat(weights[0][0]).isNaN();
    }

    @Test
    @SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.JUnitTestContainsTooManyAsserts"})
    public void smallBrainThinking() {
        final NeuronalNetwork nn = new NeuronalNetwork(1, 1, 0);
        // this.dumpWeights(nn.getConnectionWeights());

        final double[] input = new double[1];
        final double[] output = nn.think(input);
        assertSame(1, output.length, "got a 2 dimentionale output");
        assertFalse(Double.isNaN(output[0]), "got a reasonable output");
    }

    @Test
    @SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.JUnitTestContainsTooManyAsserts"})
    public void brainThinking() {
        final NeuronalNetwork nn = new NeuronalNetwork(3, 2, 3, 3);
        final double[] input = new double[3];
        final double[] output = nn.think(input);

        assertSame(2, output.length, "got a 2 dimentionale output");
        for(final double d: output) {
            assertFalse(Double.isNaN(d), "output is a Number" );
        }

    }
}
