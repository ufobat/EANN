package org.eann.sim.simulation.creature;

/**
 * Created by martin on 12.04.17.
 */
public class FeelerControls {
    protected final static int BRAIN_OUT_ARGS = 1;
    private double wantAngleOffset;

    public double getWantAngleOffset() {
        return this.wantAngleOffset;
    }

    public void fromOutputVector(final double... outputVector) {
        this.wantAngleOffset = outputVector[0];
    }
}
