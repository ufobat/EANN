package org.eann.sim.simulation.neuronalnet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class NeuronalNetwork implements Cloneable{
    private static final int BIAS_NEURON = 1;
    private final int noInNeurons;
    private final int noOutNeurons;
    private final int[] neuronsPerLayer;
    private final int noNeurons;
    private double[][] connectionWeights;
    private double[] neurons;
    private final RandomWeightGenerator weightGenerator;
    final private Random randomGenerator;

    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public NeuronalNetwork(final int noInNeurons, final int noOutNeurons, final int... neuronsPerLayer) {
        this.noNeurons = NeuronalNetwork.BIAS_NEURON + noInNeurons + noOutNeurons + IntStream.of(neuronsPerLayer).sum();
        this.neuronsPerLayer = neuronsPerLayer;
        this.noInNeurons = noInNeurons;
        this.noOutNeurons = noOutNeurons;
        this.connectionWeights = new double[this.noNeurons][this.noNeurons];
        this.neurons = new double[this.noNeurons];
        this.randomGenerator = new Random();
        this.weightGenerator = new RandomWeightGenerator(this.randomGenerator);
        this.setup();
    }

    private void setup() {
        // Bias Neuron
        this.neurons[0] = 1d;

        for (int src = 0; src < this.noNeurons; src++) {
            for (int dst = 0; dst < this.noNeurons; dst++) {

                if (src == 0) {
                    // Bias neuron connected to everything except itself.
                    if (dst == 0) {
                        this.connectionWeights[src][dst] = Double.NaN;
                    } else {
                        this.connectionWeights[src][dst] = this.weightGenerator.nextRandomWeight();
                    }

                } else {
                    final int layerOfSrc = this.layerOfNeuron(src);
                    final int layerOfDst = this.layerOfNeuron(dst);

                    if (layerOfSrc + 1 == layerOfDst) {
                        this.connectionWeights[src][dst] = this.weightGenerator.nextRandomWeight();
                    } else {
                        this.connectionWeights[src][dst] = Double.NaN;
                    }
                }
            }
        }
    }

    private int layerOfNeuron(final int neuronPosition) {
        int layer = -1;
        int searchPos = neuronPosition;

        if (searchPos >= this.noInNeurons + NeuronalNetwork.BIAS_NEURON) {
            searchPos = searchPos - (this.noInNeurons + NeuronalNetwork.BIAS_NEURON) ;

            layer++;
            while (layer < this.neuronsPerLayer.length) {
                final int layerNeurons = this.neuronsPerLayer[layer];
                if (searchPos < layerNeurons) {
                    break;
                }

                searchPos = searchPos - layerNeurons;
                layer++;
            }
        }
        return layer;
    }

    public double[][] getConnectionWeights() {
        final int length = this.connectionWeights.length;
        double[][] copy = new double[ length ][ length ];
        for (int i = 0; i < length; i++) {
            copy[i] = Arrays.copyOf(this.connectionWeights[i], length);
        }
        return copy;
    }

    public double[] think(final double... inputVector) {
        if (inputVector.length != this.noInNeurons) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < this.noInNeurons; i++) {
            this.neurons[i + NeuronalNetwork.BIAS_NEURON ] = inputVector[i];
        }
        // System.out.println("after input copy: " + Arrays.toString(this.neurons));

        for (int dstNeuronId = this.noInNeurons; dstNeuronId < this.noNeurons; dstNeuronId++) {
            double sum = 0;
            for (int inputNeuron = 0; inputNeuron < this.noNeurons; inputNeuron++) {

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
        final double[] outputVector = new double[this.noOutNeurons];
        for (int i = 0; i < this.noOutNeurons; i++) {
            outputVector[i] = this.neurons[ this.noNeurons - this.noOutNeurons + i];
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
        final MutationTypeEnum mutationType = this.getMutationType();
        mutation.mutateWeights(mutationType);
        return mutation;
    }

    public MutationTypeEnum getMutationType() {
        final double probability = this.randomGenerator.nextDouble();
        return MutationTypeEnum.getMutationTypeByProbability(probability);
    }


    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    private void mutateWeights(final MutationTypeEnum type) {
        final ArrayList<int[]> pos = new ArrayList<>();
        for (int i = 0; i < this.connectionWeights.length; i++) {
            for (int j = 0; j < this.connectionWeights[i].length; j++) {
                if (! Double.isNaN(this.connectionWeights[i][j])) {
                    pos.add(new int[] {i, j});
                }
            }
        }

        for (int i = 0; i < type.getNoOfMutations(); i++) {
            final int pick = this.randomGenerator.nextInt(pos.size());
            final int[] weightPos = pos.get(pick);

            final double weight = this.connectionWeights[ weightPos[0] ][ weightPos[1] ];
            final double multiply = Math.abs( 1 + ( this.randomGenerator.nextDouble() - 0.5) / 1250);
            final double add = (this.randomGenerator.nextDouble() - 0.5) / 1250;
            this.connectionWeights[ weightPos[0] ][ weightPos[1] ] = weight * multiply + add;
        }
    }
    public double[] getNeurons() {
        return Arrays.copyOf(this.neurons, this.neurons.length);
    }
}
