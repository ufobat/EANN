package org.eann.sim.simulation.creature;

import java.util.Arrays;

/**
 * Created by martin on 12.04.17.
 */
public class CreatureSensors {
    protected static final int BRAIN_IN_ARGS = 6;
    private double energy;
    private double age;
    private double foodLevel;
    private double water;
    private double height;
    private double hadCollision;
    private FeelerSensors[] feelerSensors;
    private double[] inputVector;


    public CreatureSensors(final FeelerSensors... feelerSensors) {
        this.feelerSensors = feelerSensors;
        this.inputVector = new double[CreatureSensors.BRAIN_IN_ARGS + feelerSensors.length * FeelerSensors.BRAIN_IN_ARGS];
    }

    public CreatureSensors(final CreatureSensors sensors) {
        this(Arrays.copyOf(sensors.feelerSensors, sensors.feelerSensors.length));
    }

    public void setEnergy(final double energy) {
        this.energy = energy;
    }

    public void setAge(final int age) {
        this.age = age;
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

    public void setHadCollision(final int hadCollision) {
        this.hadCollision = hadCollision;
    }

    public FeelerSensors getFeelerSensor(final int i) {
        return this.feelerSensors[i];
    }

    @SuppressWarnings("PMD.MethodReturnsInternalArray")
    public double[] toInputVector() {
        int i = 0;
        this.inputVector[i++] = Math.tanh(this.energy);
        this.inputVector[i++] = Math.tanh(this.age);
        this.inputVector[i++] = Math.tanh(this.foodLevel);
        this.inputVector[i++] = Math.tanh(this.water);
        this.inputVector[i++] = Math.tanh(this.height);
        this.inputVector[i++] = Math.tanh(this.hadCollision);

        for (final FeelerSensors feeler: this.feelerSensors) {
            final double[] feelerInputVector = feeler.toInputVector();
            System.arraycopy(feelerInputVector, 0, this.inputVector, i, feelerInputVector.length);
            i = i + feelerInputVector.length;
        }

        return this.inputVector;
    }
}
