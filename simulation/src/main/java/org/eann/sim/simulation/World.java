package org.eann.sim.simulation;

import org.eann.sim.configuration.CreatureSettings;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

public class World {
    private static final int SPAWN_MORE = 100;
    final private Map map;
    final private Set<Creature> creatures;

    private long date;
    private int spawns;
    private final CreatureFactory creatureFactory;
    private final CreatureSettings creatureSettings;

    public CreatureSettings getCreatureSettings() {
        return creatureSettings;
    }

    public World(final Map map, final CreatureSettings creatureSettings) {
        this.map = map;
        this.creatures = new ConcurrentSkipListSet<>();
        this.creatureFactory = new CreatureFactory();
        this.creatureSettings = creatureSettings;
        this.date = 0;
        this.spawns = 0;
    }

    public void calculateNextStep() {
        this.map.calculateNextStep();
        this.date++;
        this.spawns = 0;
        for (int i = this.creatures.size(); i < World.SPAWN_MORE; i++) {
            final Creature creature = this.creatureFactory.buildCreature(this);
            this.creatures.add(creature);
            this.spawns++;
        }

        for(final Creature creature:  this.creatures) {
            creature.calculateNextStep(this.map);
            if (creature.isDead()) {
                this.creatureFactory.disassembleCreature(creature);
                this.creatures.remove(creature);
            }

            if (creature.canGiveBirth()) {
                final Creature child = this.creatureFactory.cloneCreature(creature);
                this.creatures.add(child);
            }
        }
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
        return this.map.getWidth();
    }

    public Map getMap() {
        return this.map;
    }

    public Set<Creature> getCreatures() {
        return this.creatures;
    }

}
