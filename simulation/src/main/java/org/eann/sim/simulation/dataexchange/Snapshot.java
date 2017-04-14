package org.eann.sim.simulation.dataexchange;

import org.eann.sim.simulation.Map;

import java.util.Set;

/**
 * Created by martin on 12.04.17.
 */
public class Snapshot {

    private final Set<CreatureBean> creatures;
    private final Map map;

    public Snapshot(final Set<CreatureBean> creatures, final Map map) {
        this.creatures = creatures;
        this.map = map;
    }

    public Set<CreatureBean> getCreatures() {
        return this.creatures;
    }

    public Map getMap() {
        return this.map;
    }
}
