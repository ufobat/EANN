package org.eann.sim.simulation;

/**
 * Created by martin on 24.03.17.
 */
public class NeuronalNetwork {

    private double[][] hiddenLayer;

    NeuronalNetwork(int inputSize, int outputSize) {
        this.hiddenLayer = new double[inputSize][outputSize];
    }

    public double[] think(double[] inputVector) {
        return null;
        // Math.tanh() // tanges hyperbolicus
    }


}
