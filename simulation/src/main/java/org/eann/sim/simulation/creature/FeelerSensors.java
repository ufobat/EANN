package org.eann.sim.simulation.creature;

/**
 * Created by martin on 12.04.17.
 */
public class FeelerSensors {
    protected final static int BRAIN_IN_ARGS = 6;
    private double height;
    private double occlusion;
    private double length;
    private double angle;
    private double foodLevel;
    private double water;

    public void setOcclusion(final double occlusion) {
        this.occlusion = occlusion;
    }

    public void setLength(final int length) {
        this.length = length;
    }

    public void setAngle(final double angle) {
        this.angle = angle;
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
                this.height,
                this.occlusion,
                this.length,
                this.angle,
                this.foodLevel,
                this.water
        };
    }
}
