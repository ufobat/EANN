package org.eann.sim.simulation.neuronalnet;

import org.eann.sim.simulation.MutationTypeEnum;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by martin on 17.04.17.
 */
public final class MutationUtils {

    static private Random random = new Random();

    private MutationUtils() {
        // Utils
    }

    @SuppressWarnings({"PMD.ProhibitPublicStaticMethods", "PMD.AvoidDuplicateLiterals"})
    static public MutationTypeEnum getRandomMutation() {
        int sum = 0;
        for (final MutationTypeEnum mutation: MutationTypeEnum.values()) {
            sum = sum + mutation.getWeight();
        }
        final int pick = random.nextInt(sum);
        int current = 0;
        MutationTypeEnum value = null;
        for (final MutationTypeEnum mutation: MutationTypeEnum.values()) {
            current = current + mutation.getWeight();
            if( pick < current) {
                value = mutation;
                break;
            }
        }
        return value;
    }

    @SuppressWarnings({"PMD.ProhibitPublicStaticMethods", "PMD.AvoidDuplicateLiterals"})
    static public void mutateCreature(final NeuronalNetwork brain) {
        final MutationTypeEnum mutation = getRandomMutation();
        switch (mutation) {
            case BRAIN_CONNECTION_ADD_GAUSSIAN:
                mutateConnectionAddGaussian(brain);
                break;
            case BRAIN_CONNECTION_MULTIPLY_GAUSSIAN:
                mutateConnectionMultiplyGaussian(brain);
                break;
            /*
                TODO
            case BRAIN_CONNECTION_NEW:
                mutateConnectionNew(brain);
                break;
            case BRAIN_NEURON_NEW:
                mutateNeuronNew(brain);
                break;
                 */
            default:
                break;
        }
    }

    /*
    TODO

    private static void mutateConnectionNew(final NeuronalNetwork brain) {
        // TODO
    }

    private static void mutateNeuronNew(final NeuronalNetwork brain) {
        // final NeuronalNetwork brain = creature.getBrain();
        // double[][] connectionWeights = brain.getConnectionWeights();
        // int[] pos = pickRandomConnection(connectionWeights);
        // TODO
    }

    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public static ArrayList<int[]> possibleNewConnection(final NeuronalNetwork brain) {
        final double[][] connectionWeights = brain.getConnectionWeights();
        final int idxHidden = brain.getIdxHidden();
        final ArrayList<int[]> pos = new ArrayList<>();
        for (int src = 0; src < connectionWeights.length; src++) {
            for (int dst = idxHidden; dst < connectionWeights.length; dst++) {
                if (Double.isNaN(connectionWeights[src][dst])) {
                    pos.add(new int[] {src, dst});
                }
            }
        }
        return pos;
    }
    */
    @SuppressWarnings({"PMD.ProhibitPublicStaticMethods", "PMD.AvoidDuplicateLiterals","PMD.AvoidInstantiatingObjectsInLoops", "PMD.UseVarargs"})
    private static int[] pickRandomConnection(final double[][] connectionWeights) {
        final ArrayList<int[]> pos = new ArrayList<>();
        for (int src = 0; src < connectionWeights.length; src++) {
            for (int dst = 0; dst < connectionWeights[src].length; dst++) {
                if (! Double.isNaN(connectionWeights[src][dst])) {
                    pos.add(new int[] {src, dst});
                }
            }
        }

        final int pick = random.nextInt(pos.size());
        return pos.get(pick);
    }
    @SuppressWarnings({"PMD.ProhibitPublicStaticMethods", "PMD.AvoidDuplicateLiterals"})
    public static void mutateConnectionMultiplyGaussian(final NeuronalNetwork brain) {
        final double[][] connectionWeights = brain.getConnectionWeights();
        final int[] weightPos = MutationUtils.pickRandomConnection(connectionWeights);
        final double weight = connectionWeights[ weightPos[0] ][ weightPos[1] ];
        final double multiply =  1 + random.nextGaussian();
        connectionWeights[ weightPos[0] ][ weightPos[1] ] = weight * multiply;

        brain.setConnectionWeights(connectionWeights);
    }
    @SuppressWarnings({"PMD.ProhibitPublicStaticMethods", "PMD.AvoidDuplicateLiterals"})
    public static void mutateConnectionAddGaussian(final NeuronalNetwork brain) {
        final double[][] connectionWeights = brain.getConnectionWeights();
        final int[] weightPos = MutationUtils.pickRandomConnection(connectionWeights);
        final double weight = connectionWeights[ weightPos[0] ][ weightPos[1] ];
        connectionWeights[ weightPos[0] ][ weightPos[1] ] = weight + random.nextGaussian();

        brain.setConnectionWeights(connectionWeights);
    }

}
