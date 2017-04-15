package org.eann.sim.simulation;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.eann.sim.simulation.creature.Creature;
import org.eann.sim.simulation.creature.FamilyRegister;
import org.eann.sim.simulation.dataexchange.CreatureBean;
import org.eann.sim.simulation.dataexchange.Snapshot;
import org.eann.sim.simulation.dataexchange.StatisticsBean;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class World {
    final private Map map;
    final private ConcurrentSkipListSet<Creature> creatures;
    final private CircularFifoQueue<Creature> graveyard;
    private long date;
    private int spawns;
    private Snapshot snapshot;

    public World(final Map map) {
        this.map = map;
        this.creatures = new ConcurrentSkipListSet();
        this.graveyard = new CircularFifoQueue(100);
        this.date = 0;
        this.spawns = 0;
    }

    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public void takeSnapshot() {
        final HashSet<CreatureBean> creatures = new HashSet<>();
        for (final Creature creature: this.creatures) {
            final CreatureBean bean = new CreatureBean(creature);
            creatures.add(bean);
        }
        final StatisticsBean stats = new StatisticsBean();
        stats.setDate(this.date);
        stats.setSpawns(this.spawns);
        stats.setNoOfCreatures(this.creatures.size());

        final int size = this.graveyard.size();
        if (size != 0) {
            int deathAges = 0;
            int noOfChildren = 0;
            for(final Creature creature: this.graveyard) {
                final FamilyRegister register = creature.getRegister();
                deathAges +=  + (int) (register.getDeathDate() - register.getBirthDate());
                noOfChildren += register.getChildren().size();
            }
            stats.setAvgAgeAtDeath((double) deathAges / size);
            stats.setAvgNoOfChildren((double) noOfChildren / size);
        }

        this.snapshot = new Snapshot(creatures, new Map(this.map), stats, this.getWidth(), this.getLength());
    }

    public long getDate() {
        return this.date;
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
        this.graveyard.add(creature);
        this.creatures.remove(creature);
    }

    public void addCreature(final Creature creature) {
        this.creatures.add(creature);
    }

    public Set<Creature> getCreatures() {
        return this.creatures;
    }

    public Snapshot getSnapshot() {
        return this.snapshot;
    }
}
