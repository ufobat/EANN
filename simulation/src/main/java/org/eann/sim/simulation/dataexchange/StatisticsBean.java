package org.eann.sim.simulation.dataexchange;

import java.io.Serializable;

/**
 * Created by martin on 14.04.17.
 */
public class StatisticsBean implements Serializable{
    private static final long serialVersionUID = -5348221277280627097L;
    private long date;
    private int spawns;
    private int avgAgeAtDeath;
    private int noOfCreatures;
    private int avgNoOfChildren;

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

    public int getAvgAgeAtDeath() {
        return this.avgAgeAtDeath;
    }

    public void setAvgAgeAtDeath(final int avgAgeAtDeath) {
        this.avgAgeAtDeath = avgAgeAtDeath;
    }

    public int getNoOfCreatures() {
        return this.noOfCreatures;
    }

    public void setNoOfCreatures(final int noOfCreatures) {
        this.noOfCreatures = noOfCreatures;
    }

    public void setAvgNoOfChildren(final int avgNoOfChildren) {
        this.avgNoOfChildren = avgNoOfChildren;
    }

    public int getAvgNoOfChildren() {
        return this.avgNoOfChildren;
    }
}
