package org.eann.sim.simulation;

/**
 * Created by martin on 28.03.17.
 */
public enum MutationTypeEnum
{
    NOTHING(70),
    BRAIN_CONNECTION_ADD_GAUSSIAN(70),
    BRAIN_CONNECTION_MULTIPLY_GAUSSIAN(70),
    BRAIN_CONNECTION_NEW(20),
    BRAIN_NEURON_NEW(10);

    private final int weight;

    MutationTypeEnum(final int probability) {
        this.weight = probability;
    }

    public int getWeight() {
        return this.weight;
    }

}
