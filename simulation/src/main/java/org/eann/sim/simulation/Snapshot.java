package org.eann.sim.simulation;

import org.eann.sim.simulation.creature.CreatureState;
import java.util.Set;

/**
 * Created by martin on 12.04.17.
 */
public class Snapshot {

    private final Set<CreatureState> creatureStates;
    private final Map map;

    public Snapshot(final Set<CreatureState> creatureStates, final Map map) {
        this.creatureStates = creatureStates;
        this.map = map;
    }

    public Set<CreatureState> getCreatureStates() {
        return this.creatureStates;
    }

    public Map getMap() {
        return this.map;
    }
}
