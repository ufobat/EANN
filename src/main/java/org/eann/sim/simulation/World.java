package org.eann.sim.simulation;

import java.util.ArrayList;

/**
 * Created by martin on 19.03.17.
 */
public class World {
    private Map map;
    private ArrayList<Creature> creatures;

    public World(Map map) {
        this.map = map;
        this.creatures = new ArrayList<>();
    }

    public void calculateNextStep() {
        for(Creature creature: this.creatures) {
            creature.calculateNextStep(this.map);
        }

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
