package org.eann.sim.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by martin on 04.04.17.
 */
@XmlAccessorType(value= XmlAccessType.FIELD)
@SuppressWarnings("PMD.LongVariable")
public class CreatureSettings {
    final private FeelerSettings[] feelerSettings;
    final private int noOfHiddenLayer;
    final private int neuronsPerHiddenLayer;
    final private int bodyRadius;
    final private double startEnergy;

    public CreatureSettings() {
        this.noOfHiddenLayer = 1;
        this.neuronsPerHiddenLayer = 10;
        this.bodyRadius = 5;
        this.startEnergy = 100;
        this.feelerSettings = new FeelerSettings[] { new FeelerSettings()} ;
    }

    public int getCreatureSize() {
        int size = 0;
        for (final FeelerSettings feeler: this.feelerSettings) {
            size = Math.max(size, feeler.getLength());
        }
        return size+ this.bodyRadius;
    }

    @SuppressWarnings("PMD.MethodReturnsInternalArray")
    public FeelerSettings[] getFeelerSettings() {
        return this.feelerSettings;
    }

    public int getNoOfHiddenLayer() {
        return this.noOfHiddenLayer;
    }

    public int getNeuronsPerHiddenLayer() {
        return this.neuronsPerHiddenLayer;
    }

    public int getBodyRadius() {
        return this.bodyRadius;
    }

    public double getStartEnergy() {
        return this.startEnergy;
    }
}
