package org.eann.sim.simulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class World {
    private static final int SPAWN_MORE_CREATURES_LIMIT = 1;
    private Map map;
    private ArrayList<Creature> creatures;
    private Random randomGenerator;

    public World(Map map) {
        this.map = map;
        this.creatures = new ArrayList<>();
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
            creature.calculateNextStep(this.map);
            if (creature.isDead()) {
                iterator.remove();
            }
        }
    }

    public void spawnRandomCreature() {
        Creature creature = new Creature();
        int size = creature.getOverallRadius();

        // creature position must match the map size
        // there is a margin on each side.
        int posX = this.randomGenerator.nextInt(this.map.getWidth() - size * 2) + size;
        int posY = this.randomGenerator.nextInt(this.map.getLength() - size * 2) + size;

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

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

    public void addCreature(Creature c) {
        this.creatures.add(c);
    }
}
