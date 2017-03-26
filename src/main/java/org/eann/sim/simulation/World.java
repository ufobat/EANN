package org.eann.sim.simulation;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

public class World {
    private static final int SPAWN_MORE = 100;
    final private Map map;
    final private Set<Creature> creatures;
    final private Random randomGenerator;

    public World(final Map map) {
        this.map = map;
        this.creatures = new ConcurrentSkipListSet<>();
        this.randomGenerator = new Random();
    }

    public void calculateNextStep() {
        this.map.calculateNextStep();

        for (int i = this.creatures.size(); i < World.SPAWN_MORE; i++) {
            this.spawnRandomCreature();
        }

        final Iterator<Creature> iterator = this.creatures.iterator();
        while (iterator.hasNext()) {
            final Creature creature = iterator.next();
            creature.calculateNextStep(this.map);
            if (creature.isDead()) {
                iterator.remove();
            }

            final Creature child = creature.giveBirth();
            if (child != null) {
                this.creatures.add(child);
            }
        }
    }

    public void spawnRandomCreature() {
        final Creature creature = new Creature();
        final int size = creature.getOverallRadius();

        // creature position must match the map size
        // there is a margin on each side.
        final int posX = this.randomGenerator.nextInt(this.map.getWidth() - size * 2 - 1) + size;
        final int posY = this.randomGenerator.nextInt(this.map.getLength() - size * 2 - 1) + size;

        creature.setPosX(posX);
        creature.setPosY(posY);
        this.addCreature(creature);
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

    public void addCreature(final Creature creature) {
        this.creatures.add(creature);
    }
}
