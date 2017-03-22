package org.eann.sim.simulation;

/**
 * Created by martin on 19.03.17.
 */
public class Feeler {
    public static int NO_OF_BRAIN_IN_ARGS = 4;

    private float length;
    private float angle;
    private float occlusion;
    private float setWantToRotate;

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

    public float getOcclusion() {
        return this.occlusion;
    }

    public void setWantToRotate(float wantToRotate) {
        this.setWantToRotate = wantToRotate;
    }

    public void applyWishes() {
    }

}
