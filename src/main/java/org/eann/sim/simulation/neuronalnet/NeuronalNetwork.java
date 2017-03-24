package org.eann.sim.simulation.neuronalnet;

public class NeuronalNetwork {

    private final int noOfInputNeurons;
    private final int noOfOutputNeurons;
    private final int[] noOfNeuronsPerLayer;
    private double[][] connectionWeights;
    private double[] neurons;
    private RandomWeightGenerator randomWeightGenerator;

    public NeuronalNetwork(int noOfInputNeurons, int noOfOutputNeurons, int noOfHiddenLayer, int neuronsPerHiddenLayer) {
        int noOfNeurons = noOfInputNeurons + noOfOutputNeurons + noOfHiddenLayer * neuronsPerHiddenLayer;

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
        return null;
        // Math.tanh() // tanges hyperbolicus
    }


}
