package org.eann.sim.simulation;

/**
 * Created by martin on 19.03.17.
 */
public class Feeler {
    private float length;
    private float angle;

    public Feeler(float length, float angle) {
        this.length = length;
        this.angle = angle;
    }

    public float getLength() {
        return length;
    }

    public float getAngle() {
        return angle;
    }
}
