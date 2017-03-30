package org.eann.sim.simulation;

import java.awt.*;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

public class World {
    private static final int SPAWN_MORE = 100;
    final private Map map;
    final private Set<Creature> creatures;
    final private Random randomGenerator;
    final private ColorManager colorManager;
    private long date;
    private int spawns;

    public World(final Map map) {
        this.map = map;
        this.creatures = new ConcurrentSkipListSet<>();
        this.randomGenerator = new Random();
        this.colorManager = new ColorManager();
        this.date = 0;
        this.spawns = 0;

    }

    public void calculateNextStep() {
        this.map.calculateNextStep();
        this.date++;
        this.spawns = 0;
        for (int i = this.creatures.size(); i < World.SPAWN_MORE; i++) {
            this.spawnRandomCreature();
            this.spawns++;
        }

        for(final Creature creature:  this.creatures) {
            creature.calculateNextStep(this.map);
            if (creature.isDead()) {
                this.removeCreature(creature);
            }

            final Creature child = creature.giveBirth();
            if (child != null) {
                this.addCreature(child);
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
        Color color = creature.getColor();
        if (color == null){
            color = this.colorManager.nextColor();
            creature.setColor(color);
        } else {
            this.colorManager.incColor(color);
        }
        this.creatures.add(creature);
    }

    public void removeCreature(final Creature creature) {
        final Color color = creature.getColor();
        this.colorManager.decColor(color);
        this.creatures.remove(creature);
    }

    public long getDate() {
        return this.date;
    }

    public int getSpawns() {
        return this.spawns;
    }
}
