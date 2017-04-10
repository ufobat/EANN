package org.eann.sim.simulation;

import java.io.Serializable;

/**
 * Created by martin on 19.03.17.
 */
public class Feeler implements Serializable {
    protected final static int BRAIN_IN_ARGS = 5;
    protected final static int BRAIN_OUT_ARGS = 1;
    private static final long serialVersionUID = 3788838488634051771L;

    private final int length;
    private double angle;
    private double wantToRotate;
    // FIXME this needs to be implemented
    private final double occlusion;

    public Feeler(final int length, final double angle) {
        this.length = length;
        this.angle = angle;
        this.occlusion = 0;
    }

    @SuppressWarnings("PMD.ArrayIsStoredDirectly")
    public void setBrainOutputVector(final double[] brainOutputVector, final int startPos) {
        this.wantToRotate = brainOutputVector[startPos];
    }

    public int getSensorPosX(final int creatureX) {
        return (int) (Math.sin(this.angle) * this.length) + creatureX;
    }
    public int getSensorPosY(final int creatureY) {
        return (int) (Math.cos(this.angle) * this.length) + creatureY;
    }
    public int getLength() {
        return this.length;
    }
    public double getAngle() {
        return this.angle;
    }
    public double getOcclusion() {
        return this.occlusion;
    }

    public double getWantToRotate() {
        return this.wantToRotate;
    }

    public void setAngle(final double angle) {
        this.angle = angle;
    }
}
