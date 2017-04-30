package org.eann.sim.simulation.neuronalnet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by martin on 22.04.17.
 */
class MutationUtilsTest {

    private void testConnections(double[][] connections, double[][] clonedConns) {
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
                // System.out.println("i:" + i + " j:" + j + " " + orgRow[j] + " " + clonedRow[j]);
            }
        }
        assertEquals(1, differences);
    }

    @Test
    public void mutateConnectionAdd() {
        final NeuronalNetworkFactory factory = new NeuronalNetworkFactory();
        final NeuronalNetwork brain = factory.buildBrain(2, 2);
        final NeuronalNetwork clonedBrain = new NeuronalNetwork(brain);
        MutationUtils.mutateConnectionAddGaussian(clonedBrain);
        double [][] connections = brain.getConnectionWeights();
        double [][] clonedConns = clonedBrain.getConnectionWeights();

        this.testConnections(connections, clonedConns);
    }

    @Test
    public void mutateConnectionMultiply() {
        final NeuronalNetworkFactory factory = new NeuronalNetworkFactory();
        final NeuronalNetwork brain = factory.buildBrain(2, 2);
        final NeuronalNetwork clonedBrain = new NeuronalNetwork(brain);
        MutationUtils.mutateConnectionMultiplyGaussian(clonedBrain);
        double [][] connections = brain.getConnectionWeights();
        double [][] clonedConns = clonedBrain.getConnectionWeights();

        this.testConnections(connections, clonedConns);
    }

    @Test
    public void mutateAddConnection() {
        final NeuronalNetworkFactory factory = new NeuronalNetworkFactory();
        final NeuronalNetwork brain = factory.buildBrain(2, 2);
        final NeuronalNetwork clonedBrain = new NeuronalNetwork(brain);
        try {
            MutationUtils.mutateConnectionNew(clonedBrain);
            double[][] connections = brain.getConnectionWeights();
            double[][] clonedConns = clonedBrain.getConnectionWeights();

            this.testConnections(connections, clonedConns);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void mutateAddNeuron() {
        final NeuronalNetworkFactory factory = new NeuronalNetworkFactory();
        final NeuronalNetwork brain = factory.buildBrain(1, 1);

        try {
            double[] vector = new double[] {.0};
            double[][] connections = brain.getConnectionWeights();
            for (int i = 0; i < connections.length; i++) {
                for (int j = 0; j < connections[i].length; j++) {
                    connections[i][j] = 2;
                }
            }
            //brain.setConnectionWeights(connections);
            final NeuronalNetwork clonedBrain = new NeuronalNetwork(brain);
            MutationUtils.mutateNeuronNew(clonedBrain);
            assertEquals(brain.getIdxOut() + 1, clonedBrain.getIdxOut());
            double[][] clonedConns = clonedBrain.getConnectionWeights();

            factory.dump(brain);
            factory.dump(clonedBrain);

            double[] firstResult = brain.think(vector);
            //System.out.println( firstResult[0] + " " + firstResult[1]);
            System.out.println( firstResult[0] );
            double[] secondResult = clonedBrain.think(vector);
            //System.out.println( secondResult[0] + " " + secondResult[1]);
            System.out.println( secondResult[0] );
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
}