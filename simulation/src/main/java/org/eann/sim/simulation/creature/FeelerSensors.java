package org.eann.sim.simulation.creature;

/**
 * Created by martin on 12.04.17.
 */
public class FeelerSensors {
    protected final static int BRAIN_IN_ARGS = 6;
    private double height;
    private double occlusion;
    private double length;
    private double angleOffset;
    private double foodLevel;
    private double water;

    public void setOcclusion(final double occlusion) {
        this.occlusion = occlusion;
    }

    public void setLength(final int length) {
        this.length = length;
    }

    public void setAngleOffset(final double angleOffset) {
        this.angleOffset = angleOffset;
    }

    public void setFoodLevel(final double foodLevel) {
        this.foodLevel = foodLevel;
    }

    public void setWater(final int water) {
        this.water = water;
    }

    public void setHeight(final double height) {
        this.height = height;
    }

    public double[] toInputVector() {
        return new double[] {
                Math.tanh(this.height),
                Math.tanh(this.occlusion),
                Math.tanh(this.length),
                Math.tanh(this.angleOffset),
                Math.tanh(this.foodLevel),
                Math.tanh(this.water)
        };
    }
}
