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
        this(1, 10, 5, 100, new FeelerSettings());
    }
    public CreatureSettings(final CreatureSettings creatureSettings) {
        this(creatureSettings.noOfHiddenLayer, creatureSettings.neuronsPerHiddenLayer, creatureSettings.bodyRadius, creatureSettings.startEnergy, creatureSettings.getFeelerSettings());
    }
    public CreatureSettings(final int noOfHiddenLayer, final int neuronsPerHiddenLayer, final int bodyRadius, final double startEnergy, final FeelerSettings... feelerSettings ) {
        this.noOfHiddenLayer = noOfHiddenLayer;
        this.neuronsPerHiddenLayer = neuronsPerHiddenLayer;
        this.bodyRadius = bodyRadius;
        this.startEnergy = startEnergy;
        this.feelerSettings = feelerSettings;
    }

    public int getCreatureSize() {
        int size = 0;
        for (final FeelerSettings feeler: this.feelerSettings) {
            size = Math.max(size, feeler.getLength());
        }
        return size+ this.bodyRadius;
    }
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public FeelerSettings[] getFeelerSettings() {
        FeelerSettings[] clonedFeelerSettings = new FeelerSettings[this.feelerSettings.length];
        for (int i = 0; i < this.feelerSettings.length; i++) {
            clonedFeelerSettings[i] = new FeelerSettings(this.feelerSettings[i]);
        }
        return clonedFeelerSettings;
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
