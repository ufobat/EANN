package org.eann.sim.simulation.neuronalnet;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by martin on 24.03.17.
 */
public class NeuronalNetworkTest {

    private void dumpWeights(double[][] weights) {
        for (int src = 0; src < weights.length; src++) {
            System.out.printf("%2d: ", src);
            for (int dst = 0; dst < weights[src].length; dst++) {
                double value = weights[src][dst];
                if (Double.isNaN(value))
                    System.out.print("   NaN ");
                else
                    System.out.printf(" %+.2f ", value );

            }
            System.out.println();
        }
    }

    @Test
    public void smallBrainConnections() {
        NeuronalNetwork nn = new NeuronalNetwork(3, 2, 1, 3);
        double[][] weights = nn.getConnectionWeights();
        this.dumpWeights(weights);

        // input to hidden
        assertTrue("0 to 3", ! Double.isNaN(weights[0][3]));
        assertTrue("1 to 3", ! Double.isNaN(weights[1][3]));
        assertTrue("2 to 3", ! Double.isNaN(weights[2][3]));
        //...

        // hidden to output
        assertTrue("3 to 6", ! Double.isNaN(weights[3][6]));
        assertTrue("4 to 6", ! Double.isNaN(weights[4][6]));
        assertTrue("5 to 6", ! Double.isNaN(weights[5][6]));
        // ...

        // no connections
        assertTrue("no connection 0 to 0", Double.isNaN(weights[0][0]));
    }
}
