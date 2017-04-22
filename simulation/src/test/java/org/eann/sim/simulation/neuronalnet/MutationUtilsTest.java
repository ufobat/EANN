package org.eann.sim.simulation.neuronalnet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by martin on 22.04.17.
 */
class MutationUtilsTest {

    @Test
    public void mutateConnectionAdd() {
        final NeuronalNetworkFactory factory = new NeuronalNetworkFactory();
        final NeuronalNetwork brain = factory.buildBrain(2, 2);
        final NeuronalNetwork clonedBrain = new NeuronalNetwork(brain);
        MutationUtils.mutateConnectionAddGaussian(clonedBrain);
        double [][] connections = brain.getConnectionWeights();
        double [][] clonedConns = clonedBrain.getConnectionWeights();

        assertEquals(connections.length, clonedConns.length);
        int differences = 0;
        for (int i = 0; i <connections.length; i++) {
            final double [] orgRow = connections[i];
            final double [] clonedRow = clonedConns[i];
            assertEquals(orgRow.length, clonedRow.length);
            for (int j = 0; j < orgRow.length; j++) {
                if (!(Double.isNaN(orgRow[j]) && Double.isNaN(clonedRow[j])) && orgRow[j] != clonedRow[j]) {
                    differences++;
                }
            }
        }
        assertEquals(1, differences);
    }
}