package org.eann.sim.simulation.neuronalnet;

import org.eann.sim.simulation.MutationTypeEnum;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by martin on 17.04.17.
 */
public final class MutationUtils {

    static private Random random = new Random();
    static private RandomWeightGenerator weightGenerator = new RandomWeightGenerator(MutationUtils.random);

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

            case BRAIN_CONNECTION_NEW:
                try {
                    mutateConnectionNew(brain);
                } catch(MutationException e) {
                    mutateNeuronNew(brain);
                }
                break;
            case BRAIN_NEURON_NEW:
                mutateNeuronNew(brain);
                break;
            default:
                break;
        }
    }

    @SuppressWarnings({"PMD.AvoidInstantiatingObjectsInLoops", "PMD.UseVarargs", "PMD.AvoidDuplicateLiterals"})
    private static double[][] cloneWithNaNAt(final int at, final double[][] array) {
        double [][] newArray = new double[array.length + 1][ array.length + 1];
        int idx = 0;
        for (final double[] d: array) {
            if (idx == at) {
                double[] nan = new double[array.length + 1];
                for (int i = 0; i <nan.length; i++) {
                    nan[i] = Double.NaN;
                }
                newArray[idx++] = nan;
            }
            newArray[idx++] = cloneWithNaNAt(at, d);
        }
        return newArray;

    }
    @SuppressWarnings({"PMD.UseVarargs", "PMD.AvoidDuplicateLiterals"})
    private static double[] cloneWithNaNAt(final int at, final double[] array) {
        final double[] newArray = new double[ array.length + 1];
        int idx = 0;
        for (final double d: array) {
            if (idx == at) {
                newArray[idx++] = Double.NaN;
            }
            newArray[idx++] = d;
        }
        return newArray;
    }

    @SuppressWarnings({"PMD.ProhibitPublicStaticMethods", "PMD.AvoidDuplicateLiterals"})
    public static void mutateNeuronNew(final NeuronalNetwork brain) {
        final double[] neurons = brain.getNeurons();
        final double[][] connectionWeights = brain.getConnectionWeights();
        final int idxHidden = brain.getIdxHidden();
        final double[] newNeurons = cloneWithNaNAt(idxHidden, neurons);
        final double[][] newWeights = cloneWithNaNAt(idxHidden, connectionWeights);
        final int[] pos = pickRandomConnection(brain);

        // kill this connection but add 2 new ones.
        final double weight = newWeights[pos[0]][pos[1]];
        newWeights[pos[0]][pos[1]] = Double.NaN;

        newWeights[pos[0]][idxHidden] = 1d;
        newWeights[idxHidden][pos[1]] = weight;

        brain.setConnectionWeights(newWeights);
        brain.setNeurons(newNeurons);
        brain.setIdxOut(brain.getIdxOut() + 1);
    }

    @SuppressWarnings({"PMD.ProhibitPublicStaticMethods", "PMD.AvoidDuplicateLiterals", "PMD.AvoidInstantiatingObjectsInLoops"})
    public static void mutateConnectionNew(final NeuronalNetwork brain) throws MutationException {
        double[][] connectionWeights = brain.getConnectionWeights();
        final int idxHidden = brain.getIdxHidden();
        final ArrayList<int[]> positions = new ArrayList<>();
        for (int src = 0; src < connectionWeights.length; src++) {
            for (int dst = idxHidden; dst < connectionWeights.length; dst++) {
                if (Double.isNaN(connectionWeights[src][dst])) {
                    positions.add(new int[] {src, dst});
                }
            }
        }

        if (positions.isEmpty()) {
            throw new MutationException("Can not add new connection");
        }
        final int pick = random.nextInt(positions.size());
        final int[] pos = positions.get(pick);
        connectionWeights[pos[0]][pos[1]] = weightGenerator.nextRandomWeight();
        brain.setConnectionWeights(connectionWeights);
    }


    @SuppressWarnings({"PMD.ProhibitPublicStaticMethods", "PMD.AvoidDuplicateLiterals","PMD.AvoidInstantiatingObjectsInLoops", "PMD.UseVarargs"})
    private static int[] pickRandomConnection(final NeuronalNetwork brain) {
        final List<int[]> pos = brain.getConnections();
        final int pick = random.nextInt(pos.size());
        return pos.get(pick);
    }
    @SuppressWarnings({"PMD.ProhibitPublicStaticMethods", "PMD.AvoidDuplicateLiterals"})
    public static void mutateConnectionMultiplyGaussian(final NeuronalNetwork brain) {
        final double[][] connectionWeights = brain.getConnectionWeights();
        final int[] weightPos = MutationUtils.pickRandomConnection(brain);
        final double weight = connectionWeights[ weightPos[0] ][ weightPos[1] ];
        final double multiply =  1 + random.nextGaussian();
        connectionWeights[ weightPos[0] ][ weightPos[1] ] = weight * multiply;
        brain.setConnectionWeights(connectionWeights);
    }
    @SuppressWarnings({"PMD.ProhibitPublicStaticMethods", "PMD.AvoidDuplicateLiterals"})
    public static void mutateConnectionAddGaussian(final NeuronalNetwork brain) {
        final double[][] connectionWeights = brain.getConnectionWeights();
        final int[] weightPos = MutationUtils.pickRandomConnection(brain);
        final double weight = connectionWeights[ weightPos[0] ][ weightPos[1] ];
        connectionWeights[ weightPos[0] ][ weightPos[1] ] = weight + random.nextGaussian();
        brain.setConnectionWeights(connectionWeights);
    }

}
