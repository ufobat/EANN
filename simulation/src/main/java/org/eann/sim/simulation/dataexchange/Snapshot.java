package org.eann.sim.simulation.dataexchange;

import org.eann.sim.simulation.Map;

import java.util.Set;

/**
 * Created by martin on 12.04.17.
 */
public class Snapshot {

    private final Set<CreatureBean> creatures;
    private final Map map;
    private final StatisticsBean stats;
    private final int width;
    private final int length;

    public Snapshot(final Set<CreatureBean> creatures, final Map map, final StatisticsBean stats, final int width, final int length) {
        this.creatures = creatures;
        this.map = map;
        this.stats = stats;
        this.width = width;
        this.length = length;
    }

    public Set<CreatureBean> getCreatures() {
        return this.creatures;
    }

    public Map getMap() {
        return this.map;
    }

    public StatisticsBean getStats() {
        return this.stats;
    }

    public int getWidth() {
        return this.width;
    }

    public int getLength() {
        return this.length;
    }
}
