package org.eann.sim.simulation.creature;

import java.util.Arrays;

/**
 * Created by martin on 12.04.17.
 */
public class CreatureControls {
    protected static final int BRAIN_OUT_ARGS = 4;
    private double wantToEat;
    private double wantToGiveBirth;
    private double wantToAccelerate;
    private double wantToRotate;
    final private FeelerControls[] feelerControls;

    public CreatureControls(final FeelerControls... feelerControls) {
        this.feelerControls = feelerControls;
    }

    public CreatureControls(final CreatureControls controls) {
        this(Arrays.copyOf(controls.feelerControls, controls.feelerControls.length));
    }

    public double getWantToEat() {
        return this.wantToEat;
    }

    public double getWantToGiveBirth() {
        return this.wantToGiveBirth;
    }

    public double getWantToAccelerate() {
        return this.wantToAccelerate;
    }

    public double getWantToRotate() {
        return this.wantToRotate;
    }

    public FeelerControls getFeelerControls(final int i) {
        return this.feelerControls[i];
    }

    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public void fromOutputVector(final double... outputVector) {
        int i = 0;
        this.wantToEat = outputVector[i++];
        this.wantToGiveBirth = outputVector[i++];
        this.wantToAccelerate = outputVector[i++];
        this.wantToRotate = outputVector[i++];
        for (final FeelerControls feelerControl: this.feelerControls) {
            final double[] fov = new double[FeelerControls.BRAIN_OUT_ARGS];
            System.arraycopy(outputVector, i, fov, 0, FeelerControls.BRAIN_OUT_ARGS);
            i = i + FeelerControls.BRAIN_OUT_ARGS;
            feelerControl.fromOutputVector(fov);
        }
    }
}
