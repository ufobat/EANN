package org.eann.sim.simulation.neuronalnet;

public class NeuronalNetwork implements Cloneable{

    private final int noOfInputNeurons;
    private final int noOfOutputNeurons;
    private final int[] noOfNeuronsPerLayer;
    private final int noOfNeurons;
    private double[][] connectionWeights;
    private double[] neurons;
    private final RandomWeightGenerator randomWeightGenerator;

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

                final int layerOfSrc = this.layerOfNeuron(src);
                final int layerOfDst = this.layerOfNeuron(dst);

                if (layerOfSrc + 1 == layerOfDst) {
                    this.connectionWeights[src][dst] = this.randomWeightGenerator.nextRandomWeight();
                } else {
                    this.connectionWeights[src][dst] = Double.NaN;
                }
            }
        }
    }

    private int layerOfNeuron(final int neuronPosition) {
        int layer = -1;
        int searchPos = neuronPosition;

        if (searchPos >= this.noOfInputNeurons) {
            searchPos = searchPos - this.noOfInputNeurons;

            layer++;
            while (layer < this.noOfNeuronsPerLayer.length) {
                final int neuronsInThisLayer = this.noOfNeuronsPerLayer[layer];
                if (searchPos < neuronsInThisLayer) {
                    break;
                }

                searchPos = searchPos - neuronsInThisLayer;
                layer++;
            }
        }
        return layer;
    }

    public double[][] getConnectionWeights() {
        return connectionWeights;
    }

    public double[] think(double... inputVector) {
        if (inputVector.length != this.noOfInputNeurons) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < this.noOfInputNeurons; i++) {
            this.neurons[i] = inputVector[i];
        }
        // System.out.println("after input copy: " + Arrays.toString(this.neurons));

        for (int dstNeuronId = this.noOfInputNeurons; dstNeuronId < this.noOfNeurons; dstNeuronId++) {
            double sum = 0;
            for (int inputNeuron = 0; inputNeuron < this.noOfNeurons; inputNeuron++) {

                final double neuron = this.neurons[inputNeuron];
                final double weight = this.connectionWeights[inputNeuron][dstNeuronId];
                if (! Double.isNaN(weight)) {
                    // System.out.printf("src:%s dst:%s srcNeuronLevel:%s weight:%s\n", inputNeuron, dstNeuronId, neuron, weight);
                    sum = sum + neuron*weight;
                }
            }
            // System.out.printf("neuron: %s sum = %s, output = %s\n", dstNeuronId, sum, Math.tanh(sum));
            this.neurons[dstNeuronId] = Math.tanh(sum);
        }
        // System.out.println("after calculations: " + Arrays.toString(this.neurons));
        final double[] outputVector = new double[this.noOfOutputNeurons];
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
            // FIXME logger
            // e.printStackTrace();
        }
        mutation.mutateWeights();
        return mutation;
    }

    private void mutateWeights() {
        for (int i = 0; i < this.connectionWeights.length; i++) {
            for (int j = 0; j < this.connectionWeights[i].length; j++) {
                final double weight = this.connectionWeights[i][j];
                final double multiplicativeFactor = Math.abs( 1 + this.randomWeightGenerator.nextDouble() / 1250);
                final double additiveFactor = this.randomWeightGenerator.nextDouble() / 1250;
                this.connectionWeights[i][j] = weight * multiplicativeFactor + additiveFactor;
            }
        }
    }
}
