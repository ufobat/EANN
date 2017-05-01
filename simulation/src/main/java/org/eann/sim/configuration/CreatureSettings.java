package org.eann.sim.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by martin on 04.04.17.
 */
@XmlAccessorType(value= XmlAccessType.FIELD)
@SuppressWarnings("PMD.LongVariable")
public class CreatureSettings {
    private int feelerLength;
    private int noOfFeeler;
    private int noOfHiddenLayer;
    private int neuronsPerHiddenLayer;
    private int bodyRadius;
    final private double startEnergy;
    private double speedFactor;

    public CreatureSettings() {
        this(1, 10, 5, 100, 15, 1, 20);
    }
    public CreatureSettings(final CreatureSettings creatureSettings) {
        this(creatureSettings.noOfHiddenLayer, creatureSettings.neuronsPerHiddenLayer, creatureSettings.bodyRadius, creatureSettings.startEnergy, creatureSettings.getFeelerLength(), creatureSettings.getNoOfFeeler(), creatureSettings.getSpeedFactor());
    }
    public CreatureSettings(final int noOfHiddenLayer, final int neuronsPerHiddenLayer, final int bodyRadius, final double startEnergy, final int feelerLength, final int noOfFeeler, final double speedFactor) {
        this.noOfHiddenLayer = noOfHiddenLayer;
        this.neuronsPerHiddenLayer = neuronsPerHiddenLayer;
        this.bodyRadius = bodyRadius;
        this.startEnergy = startEnergy;
        this.feelerLength = feelerLength;
        this.noOfFeeler = noOfFeeler;
        this.speedFactor = speedFactor;
    }

    public void applyConfiguration(final CreatureSettings creatureSettings) {
        this.setBodyRadius(creatureSettings.getBodyRadius());
        this.setNeuronsPerHiddenLayer(creatureSettings.getNeuronsPerHiddenLayer());
        this.setNoOfHiddenLayer(creatureSettings.getNoOfHiddenLayer());
        this.setFeelerLength(creatureSettings.getFeelerLength());
        this.setNoOfFeeler(creatureSettings.getNoOfFeeler());
        this.setSpeedFactor(creatureSettings.getSpeedFactor());
    }

    public int getCreatureSize() {
        return Math.max(this.getFeelerLength(), this.getBodyRadius());
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

    public void setBodyRadius(final int bodyRadius) {
        this.bodyRadius = bodyRadius;
    }

    public void setNoOfHiddenLayer(final int noOfHiddenLayer) {
        this.noOfHiddenLayer = noOfHiddenLayer;
    }

    public void setNeuronsPerHiddenLayer(final int neuronsPerHiddenLayer) {
        this.neuronsPerHiddenLayer = neuronsPerHiddenLayer;
    }

    public int getFeelerLength() {
        return this.feelerLength;
    }

    public int getNoOfFeeler() {
        return this.noOfFeeler;
    }

    public void setFeelerLength(final int feelerLength) {
        this.feelerLength = feelerLength;
    }

    public void setNoOfFeeler(final int noOfFeeler) {
        this.noOfFeeler = noOfFeeler;
    }

    public double getSpeedFactor() {
        return this.speedFactor;
    }

    public void setSpeedFactor(final double speedFactor) {
        this.speedFactor = speedFactor;
    }
}
