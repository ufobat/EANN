package org.eann.sim.simulation.neuronalnet;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

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
        assertFalse("0 to 3", Double.isNaN(weights[0][3]));
        assertFalse("1 to 3", Double.isNaN(weights[1][3]));
        assertFalse("2 to 3", Double.isNaN(weights[2][3]));
        //...

        // hidden to output
        assertFalse("3 to 6", Double.isNaN(weights[3][6]));
        assertFalse("4 to 6", Double.isNaN(weights[4][6]));
        assertFalse("5 to 6", Double.isNaN(weights[5][6]));
        // ...

        // no connections
        assertTrue("no connection 0 to 0", Double.isNaN(weights[0][0]));
    }

    @Test
    @SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.JUnitTestContainsTooManyAsserts"})
    public void smallBrainThinking() {
        final NeuronalNetwork nn = new NeuronalNetwork(1, 1, 0);
        // this.dumpWeights(nn.getConnectionWeights());

        final double[] input = new double[1];
        final double[] output = nn.think(input);
        assertSame("got a 2 dimentionale output", 1, output.length);
        assertFalse("got a reasonable output", Double.isNaN(output[0]));
    }

    @Test
    @SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.JUnitTestContainsTooManyAsserts"})
    public void brainThinking() {
        final NeuronalNetwork nn = new NeuronalNetwork(3, 2, 3, 3);
        final double[] input = new double[3];
        final double[] output = nn.think(input);

        assertSame("got a 2 dimentionale output", 2, output.length);
        for(final double d: output) {
            assertFalse("output is a Number", Double.isNaN(d) );
        }

    }
}
