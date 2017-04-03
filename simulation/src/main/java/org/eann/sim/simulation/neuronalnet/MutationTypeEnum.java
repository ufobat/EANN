package org.eann.sim.simulation.neuronalnet;

/**
 * Created by martin on 28.03.17.
 */
public enum MutationTypeEnum
{
    NO(0, 0.3),
    SMALL(1, 0.9),
    LARGE(2, 1);

    private final int noOfMutations;
    private final double boundary;

    MutationTypeEnum(final int noOfMutations, final double boundary) {
        this.noOfMutations = noOfMutations;
        this.boundary = boundary;
    }

    public int getNoOfMutations() {
        return this.noOfMutations;
    }

    public double getBoundary() {
        return this.boundary;
    }

    @SuppressWarnings("PMD.ProhibitPublicStaticMethods")
    public static MutationTypeEnum getMutationTypeByProbability(final double probability) {
        MutationTypeEnum returnType;
        if (probability < MutationTypeEnum.NO.getBoundary())
        {
            returnType = MutationTypeEnum.NO;
        } else if(probability < MutationTypeEnum.SMALL.getBoundary()) {
            returnType = MutationTypeEnum.SMALL;
        } else {
            returnType = MutationTypeEnum.LARGE;
        }
        return returnType;
    }
}
