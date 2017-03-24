package org.eann.sim.simulation;

import java.util.ArrayList;
import java.util.Random;

public class World {
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
        for(Creature creature: this.creatures) {
            creature.calculateNextStep(this.map);
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
