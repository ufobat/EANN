package org.eann.sim.simulation.dataexchange;

import java.io.Serializable;

/**
 * Created by martin on 14.04.17.
 */
public class StatisticsBean implements Serializable{
    private static final long serialVersionUID = -5348221277280627097L;
    private long date;
    private int spawns;
    private double avgAgeAtDeath;
    private int noOfCreatures;
    private double avgNoOfChildren;
    private int maxAgeAtDeath;
    private int maxNoOfChildren;

    public long getDate() {
        return this.date;
    }

    public void setDate(final long date) {
        this.date = date;
    }

    public int getSpawns() {
        return this.spawns;
    }

    public void setSpawns(final int spawns) {
        this.spawns = spawns;
    }

    public double getAvgAgeAtDeath() {
        return this.avgAgeAtDeath;
    }

    public void setAvgAgeAtDeath(final double avgAgeAtDeath) {
        this.avgAgeAtDeath = avgAgeAtDeath;
    }

    public int getNoOfCreatures() {
        return this.noOfCreatures;
    }

    public void setNoOfCreatures(final int noOfCreatures) {
        this.noOfCreatures = noOfCreatures;
    }

    public void setAvgNoOfChildren(final double avgNoOfChildren) {
        this.avgNoOfChildren = avgNoOfChildren;
    }

    public double getAvgNoOfChildren() {
        return this.avgNoOfChildren;
    }

    public void setMaxAgeAtDeath(final int maxAgeAtDeath) {
        this.maxAgeAtDeath = maxAgeAtDeath;
    }

    public void setMaxNoOfChildren(final int maxNoOfChildren) {
        this.maxNoOfChildren = maxNoOfChildren;
    }

    public int getMaxAgeAtDeath() {
        return this.maxAgeAtDeath;
    }

    public int getMaxNoOfChildren() {
        return this.maxNoOfChildren;
    }
}
