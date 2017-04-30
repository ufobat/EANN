package org.eann.sim.simulation.neuronalnet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NeuronalNetwork implements Cloneable, Serializable {
    public static final int BIAS_NEURON = 1;
    private static final long serialVersionUID = -2556179297332938067L;
    private final int idxHidden;
    private int idxOut;

    private double[][] connectionWeights;
    private double[] neurons;

    @SuppressWarnings({"PMD.ArrayIsStoredDirectly", "PMD.AvoidDuplicateLiterals"})
    public NeuronalNetwork(final double[][] connectionWeights, final double[] neurons, final int idxHidden, final int idxOut) {
        this.connectionWeights = connectionWeights;
        this.neurons = neurons;
        this.idxHidden = idxHidden;
        this.idxOut = idxOut;
    }

    public NeuronalNetwork(final NeuronalNetwork b) {
        this(b.getConnectionWeights(),b.getNeurons(), b.idxHidden, b.idxOut);
    }

    public double[] think(final double... inputVector) {
        if (inputVector.length + NeuronalNetwork.BIAS_NEURON != this.idxHidden) {
            throw new IllegalArgumentException("Expected " + (this.idxHidden - 1) + " parameter in input vector, but got " + inputVector.length);
        }
        for (int i = 0; i < inputVector.length; i++) {
            this.neurons[i + NeuronalNetwork.BIAS_NEURON] = inputVector[i];
        }
        // System.out.println("after input copy: " + Arrays.toString(this.neurons));

        for (int dstNeuronId = this.idxHidden; dstNeuronId < this.neurons.length; dstNeuronId++) {
            double sum = 0;
            for (int inputNeuron = 0; inputNeuron < this.neurons.length; inputNeuron++) {

                final double neuron = this.neurons[inputNeuron];
                //  System.out.printf("idx=%s neuron=%s\n", inputNeuron, neuron);
                final double weight = this.connectionWeights[inputNeuron][dstNeuronId];
                if (!Double.isNaN(weight)) {
                    // System.out.printf("src:%s dst:%s srcNeuronLevel:%s weight:%s\n", inputNeuron, dstNeuronId, neuron, weight);
                    sum = sum + neuron * weight;
                }
            }
            // System.out.printf("neuron: %s sum = %s, output = %s\n", dstNeuronId, sum, Math.tanh(sum));
            this.neurons[dstNeuronId] = Math.tanh(sum);
        }
        // System.out.println("after calculations: " + Arrays.toString(this.neurons));
        final int noOfOut = this.neurons.length - this.idxOut;
        final double[] outputVector = new double[noOfOut];
        for (int i = 0; i < noOfOut; i++) {
            outputVector[i] = this.neurons[this.idxOut + i];
        }
        // System.out.println("output: " + Arrays.toString(outputVector));
        return outputVector;
    }

    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public List<int[]> getConnections() {
        final ArrayList<int[]> pos = new ArrayList<>();
        for (int src = 0; src < connectionWeights.length; src++) {
            for (int dst = 0; dst < connectionWeights[src].length; dst++) {
                if (! Double.isNaN(connectionWeights[src][dst])) {
                    pos.add(new int[] {src, dst});
                }
            }
        }
        return pos;
    }

    public double[] getNeurons() {
        return Arrays.copyOf(this.neurons, this.neurons.length);
    }

    public double[][] getConnectionWeights() {
        final int length = this.connectionWeights.length;
        double[][] copy = new double[ length ][ length ];
        for (int i = 0; i < length; i++) {
            copy[i] = Arrays.copyOf(this.connectionWeights[i], length);
        }
        return copy;
    }

    @SuppressWarnings({"PMD.ArrayIsStoredDirectly", "PMD.AvoidDuplicateLiterals", "PMD.UseVarargs"})
    public void setConnectionWeights(final double[][] connectionWeights) {
        this.connectionWeights = connectionWeights;
    }

    public int getIdxHidden() {
        return this.idxHidden;
    }

    public int getIdxOut() {
        return this.idxOut;
    }

    @SuppressWarnings({"PMD.ArrayIsStoredDirectly", "PMD.AvoidDuplicateLiterals", "PMD.UseVarargs"})
    public void setNeurons(final double[] neurons) {
        this.neurons = neurons;
    }

    public void setIdxOut(final int idxOut) {
        this.idxOut = idxOut;
    }
}
