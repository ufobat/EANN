package org.eann.sim.simulation.neuronalnet;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by martin on 19.04.17.
 */
class NeuronalNetworkFactoryTest {

    @Test
    void buildBrain() {
        NeuronalNetworkFactory factory = new NeuronalNetworkFactory();
        NeuronalNetwork brain = factory.buildBrain(1, 1);
    }

    @Test
    public void smallBrainConnections() {
        final NeuronalNetworkFactory factory = new NeuronalNetworkFactory();
        final NeuronalNetwork brain = factory.buildBrain(1,1);
        final double[][] connections = brain.getConnectionWeights();

        Assertions.assertThat(connections[0][0]).isNaN();
        Assertions.assertThat(connections[0][1]).isNaN();
        Assertions.assertThat(connections[0][2]).isNotNaN();  // bias
        Assertions.assertThat(connections[1][0]).isNaN();
        Assertions.assertThat(connections[1][1]).isNaN();
        Assertions.assertThat(connections[1][2]).isNotNaN();  // in to out
        Assertions.assertThat(connections[2][0]).isNaN();
        Assertions.assertThat(connections[2][1]).isNaN();
        Assertions.assertThat(connections[2][2]).isNaN();
    }

    @Test
    public void smallBrainNeurons() {
        final NeuronalNetworkFactory factory = new NeuronalNetworkFactory();
        final NeuronalNetwork brain = factory.buildBrain(1,1);
        final double[] neurons = brain.getNeurons();
        assertEquals(3, neurons.length, "got 3 neurons");
    }

    @Test
    public void smallBrainIndexHiddenLayer() {
        final NeuronalNetworkFactory factory = new NeuronalNetworkFactory();
        final NeuronalNetwork brain = factory.buildBrain(1,1);
        final int idxHidden = brain.getIdxHidden();

        assertEquals(2, idxHidden);
    }

    @Test
    public void smallBrainIndexOutLayer() {
        final NeuronalNetworkFactory factory = new NeuronalNetworkFactory();
        final NeuronalNetwork brain = factory.buildBrain(1,1);
        final int idxOut = brain.getIdxOut();

        assertEquals(2, idxOut);
    }

}