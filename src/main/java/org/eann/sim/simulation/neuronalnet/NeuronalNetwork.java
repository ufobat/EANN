package org.eann.sim.simulation.neuronalnet;

public class NeuronalNetwork implements Cloneable{

    private final int noOfInputNeurons;
    private final int noOfOutputNeurons;
    private final int[] noOfNeuronsPerLayer;
    private final int noOfNeurons;
    private double[][] connectionWeights;
    private double[] neurons;
    private RandomWeightGenerator randomWeightGenerator;

    public NeuronalNetwork(int noOfInputNeurons, int noOfOutputNeurons, int noOfHiddenLayer, int neuronsPerHiddenLayer) {
        this.noOfNeurons = noOfInputNeurons + noOfOutputNeurons + noOfHiddenLayer * neuronsPerHiddenLayer;
        this.noOfInputNeurons = noOfInputNeurons;
        this.noOfOutputNeurons = noOfOutputNeurons;
        this.noOfNeuronsPerLayer = new int[noOfHiddenLayer];
        for (int layer = 0; layer < noOfHiddenLayer; layer++) {
            this.noOfNeuronsPerLayer[layer] = neuronsPerHiddenLayer;
        }

        this.connectionWeights = new double[noOfNeurons][noOfNeurons];
        this.neurons = new double[noOfNeurons];
        this.randomWeightGenerator = new RandomWeightGenerator();

        for (int src = 0; src < noOfNeurons; src++) {
            for (int dst = 0; dst < noOfNeurons; dst++) {

                int layerOfSrc = this.layerOfNeuron(src);
                int layerOfDst = this.layerOfNeuron(dst);

                if (layerOfSrc + 1 == layerOfDst) {
                    this.connectionWeights[src][dst] = this.randomWeightGenerator.nextRandomWeight();
                } else {
                    this.connectionWeights[src][dst] = Double.NaN;
                }
            }
        }
    }

    private int layerOfNeuron(int neuronPosition) {
        int layer = -1;
        if (neuronPosition < this.noOfInputNeurons) {
            return layer;
        }
        neuronPosition = neuronPosition - this.noOfInputNeurons;

        layer++;
        while(layer < this.noOfNeuronsPerLayer.length) {
            int neuronsInThisLayer = this.noOfNeuronsPerLayer[layer];
            if (neuronPosition < neuronsInThisLayer)
                return layer;

            neuronPosition = neuronPosition - neuronsInThisLayer;
            layer++;
        }

        return layer;
    }

    public double[][] getConnectionWeights() {
        return connectionWeights;
    }

    public double[] think(double[] inputVector) {
        if (inputVector.length != this.noOfInputNeurons)
            throw new IllegalArgumentException();

        for (int i = 0; i < this.noOfInputNeurons; i++) {
            this.neurons[i] = inputVector[i];
        }
        // System.out.println("after input copy: " + Arrays.toString(this.neurons));

        for (int dstNeuronId = this.noOfInputNeurons; dstNeuronId < this.noOfNeurons; dstNeuronId++) {
            double sum = 0;
            for (int inputNeuron = 0; inputNeuron < this.noOfNeurons; inputNeuron++) {

                double neuron = this.neurons[inputNeuron];
                double weight = this.connectionWeights[inputNeuron][dstNeuronId];
                if (! Double.isNaN(weight)) {
                    // System.out.printf("src:%s dst:%s srcNeuronLevel:%s weight:%s\n", inputNeuron, dstNeuronId, neuron, weight);
                    sum = sum + neuron*weight;
                }
            }
            // System.out.printf("neuron: %s sum = %s, output = %s\n", dstNeuronId, sum, Math.tanh(sum));
            this.neurons[dstNeuronId] = Math.tanh(sum);
        }
        // System.out.println("after calculations: " + Arrays.toString(this.neurons));
        double[] outputVector = new double[this.noOfOutputNeurons];
        for (int i = 0; i < this.noOfOutputNeurons; i++) {
            outputVector[i] = this.neurons[ this.noOfNeurons - this.noOfOutputNeurons + i];
        }
        // System.out.println("output: " + Arrays.toString(outputVector));
        return outputVector;
    }


    public NeuronalNetwork getMutation() {
        NeuronalNetwork mutation = null;
        try {
            mutation = (NeuronalNetwork) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        mutation.mutateWeights();
        return mutation;
    }

    private void mutateWeights() {
        for (int i = 0; i < this.connectionWeights.length; i++) {
            for (int j = 0; j < this.connectionWeights[i].length; j++) {
                double weight = this.connectionWeights[i][j];
                double multiplicativeFactor = ( 1 + this.randomWeightGenerator.nextDouble() / 25);
                double additiveFactor = this.randomWeightGenerator.nextDouble() / 25;
                this.connectionWeights[i][j] = weight * multiplicativeFactor + additiveFactor;
            }
        }
    }
}
