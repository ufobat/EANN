package org.eann.sim.simulation.dataexchange;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by martin on 14.04.17.
 */
public class StatisticsBean implements Serializable{
    private static final long serialVersionUID = -5348221277280627097L;
    private long date;
    private int spawns;
    private int noOfCreatures;
    private List<Integer> ages;
    private List<Integer> noOfChildren;

    public StatisticsBean() {
        this.ages = new ArrayList();
        this.noOfChildren = new ArrayList();
    }

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

    public int getNoOfCreatures() {
        return this.noOfCreatures;
    }

    public void setNoOfCreatures(final int noOfCreatures) {
        this.noOfCreatures = noOfCreatures;
    }

    public void setAges(final List<Integer> ages) {
        this.ages = ages;
    }

    public void setNoOfChildren(final List<Integer> noOfChildren) {
        this.noOfChildren = noOfChildren;
    }

    public List<Integer> getAges() {
        return this.ages;
    }

    public List<Integer> getNoOfChildren() {
        return this.noOfChildren;
    }
}
