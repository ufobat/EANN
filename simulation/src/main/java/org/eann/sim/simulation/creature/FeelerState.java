package org.eann.sim.simulation.creature;

import java.io.Serializable;

/**
 * Created by martin on 19.03.17.
 */
public class FeelerState implements Serializable {
    private static final long serialVersionUID = 3788838488634051771L;

    private final int length;
    private double angle;
    // FIXME this needs to be implemented
    private final double occlusion;

    public FeelerState(final int length, final double angle, final double occlusion) {
        this.length = length;
        this.angle = angle;
        this.occlusion = occlusion;
    }

    public FeelerState(final FeelerState s) {
        this(s.length, s.angle, s.occlusion);
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
    public void setAngle(final double angle) {
        this.angle = angle;
    }
}
