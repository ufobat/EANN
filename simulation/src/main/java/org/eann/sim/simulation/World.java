package org.eann.sim.simulation;

import org.eann.sim.simulation.creature.Creature;
import org.eann.sim.simulation.dataexchange.CreatureBean;
import org.eann.sim.simulation.dataexchange.Snapshot;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class World {
    final private Map map;
    final private ConcurrentSkipListSet<Creature> creatures;
    private long date;
    private int spawns;

    public World(final Map map) {
        this.map = map;
        this.creatures = new ConcurrentSkipListSet<>();
        this.date = 0;
        this.spawns = 0;
    }

    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public Snapshot getSnapshot() {
        // FIXME concurrent exceptions
        final HashSet<CreatureBean> creatures = new HashSet<>();
        for (final Creature creature: this.creatures) {
            final CreatureBean bean = new CreatureBean(creature);
            creatures.add(bean);
        }
        return new Snapshot(creatures, new Map(this.map));
    }

    public long getDate() {
        return this.date;
    }

    public int getSpawns() {
        return this.spawns;
    }

    public int getWidth() {
        return this.map.getWidth();
    }

    public int getLength () {
        return this.map.getLength();
    }

    public Map getMap() {
        return this.map;
    }

    public void increaseDate() {
        this.date++;
    }

    public void setSpawns(final int spawns) {
        this.spawns = spawns;
    }

    public void removeCreature(final Creature creature) {
        this.creatures.remove(creature);
    }

    public void addCreature(final Creature creature) {
        this.creatures.add(creature);
    }

    public Set<Creature> getCreatures() {
        return this.creatures;
    }
}
