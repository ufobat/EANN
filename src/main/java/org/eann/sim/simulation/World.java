package org.eann.sim.simulation;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

public class World {
    private static final int SPAWN_MORE_CREATURES_LIMIT = 100;
    private Map map;
    private Set<Creature> creatures;
    private Random randomGenerator;

    public World(Map map) {
        this.map = map;
        this.creatures = new ConcurrentSkipListSet<>();
        this.randomGenerator = new Random();
    }

    public void calculateNextStep() {
        this.map.calculateNextStep();

        if ( this.creatures.size() < SPAWN_MORE_CREATURES_LIMIT) {
            this.spawnRandomCreature();
        }

        Iterator<Creature> iterator = this.creatures.iterator();
        while(iterator.hasNext()) {
            Creature creature = iterator.next();
            try {
                creature.calculateNextStep(this.map);
                if (creature.isDead()) {
                    iterator.remove();
                }

                Creature child = creature.giveBirth();
                if (child != null) {
                    this.creatures.add(child);
                }

            }catch(ArrayIndexOutOfBoundsException ex) {
                System.out.printf("creature x=%s y=%s of malwidth=%s maplength=%s", creature.getPosX(), creature.getPosY(), this.map.getWidth(), this.map.getLength());
                throw ex;
            }
        }
    }

    public void spawnRandomCreature() {
        Creature creature = new Creature();
        int size = creature.getOverallRadius();

        // creature position must match the map size
        // there is a margin on each side.
        int posX = this.randomGenerator.nextInt(this.map.getWidth() - size * 2 - 1) + size;
        int posY = this.randomGenerator.nextInt(this.map.getLength() - size * 2 - 1) + size;

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
        return map;
    }

    public Set<Creature> getCreatures() {
        return this.creatures;
    }

    public void addCreature(Creature c) {
        this.creatures.add(c);
    }
}
