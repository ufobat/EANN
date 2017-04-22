package org.eann.sim.simulation.neuronalnet;

import java.util.Random;

/**
 * Created by martin on 17.04.17.
 */
public class NeuronalNetworkFactory {

    private final RandomWeightGenerator weightGenerator;

    public NeuronalNetworkFactory() {
        this.weightGenerator = new RandomWeightGenerator(new Random());
    }

    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public NeuronalNetwork buildBrain(final int noInNeurons, final int noOutNeurons) {
        final int noNeurons = NeuronalNetwork.BIAS_NEURON + noInNeurons + noOutNeurons;
        final int idxNeuronsOut = noInNeurons + NeuronalNetwork.BIAS_NEURON;

        final double[][] connectionWeights = new double[noNeurons][noNeurons];
        final double[] neurons = new double[noNeurons];

        // Bias Neuron
        neurons[0] = 1d;

        for (int src = 0; src < noNeurons; src++) {
            for (int dst = 0; dst < noNeurons; dst++) {

                if (src < idxNeuronsOut && dst >= idxNeuronsOut) {
                    connectionWeights[src][dst] = weightGenerator.nextRandomWeight();
                } else {
                    connectionWeights[src][dst] = Double.NaN;
                }
            }
        }

        return new NeuronalNetwork(connectionWeights, neurons, idxNeuronsOut, idxNeuronsOut);
    }

    @SuppressWarnings("PMD.SystemPrintln")
    public void dump(final NeuronalNetwork brain) {
        final double[][] weights = brain.getConnectionWeights();

        System.out.print("     ");
        for(int i = 0; i < weights.length; i++) {
            System.out.printf("  %2d:  ", i);
        }
        System.out.println();

        for (int src = 0; src < weights.length; src++) {
            System.out.printf("%2d: ", src);
            for (int dst = 0; dst < weights[src].length; dst++) {
                final double value = weights[src][dst];
                if (Double.isNaN(value)) {
                    System.out.print("   NaN ");
                }
                else {
                    System.out.printf(" %+.2f ", value);
                }
            }
            System.out.println();
        }
    }
}
