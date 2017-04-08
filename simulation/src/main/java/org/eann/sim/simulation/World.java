package org.eann.sim.simulation;

import org.eann.sim.configuration.Config;
import org.eann.sim.configuration.CreatureSettings;
import org.eann.sim.configuration.RulesSettings;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

public class World {
    final private Map map;
    final private Set<Creature> creatures;

    private long date;
    private int spawns;
    private final CreatureFactory creatureFactory;
    private final Config config;

    public CreatureSettings getCreatureSettings() {
        return this.config.getCreatureSettings();
    }

    public World(final Map map, final Config config) {
        this.map = map;
        this.creatures = new ConcurrentSkipListSet<>();
        this.creatureFactory = new CreatureFactory();
        this.config = config;
        this.date = 0;
        this.spawns = 0;
    }

    private void spawnCreature() {
        final Creature creature = this.creatureFactory.buildCreature(this);
        this.creatures.add(creature);
        this.spawns++;
    }

    public void calculateNextStep() {
        final RulesSettings rulesSettings = this.config.getRulesSettings();
        this.map.calculateNextStep();
        this.date++;
        this.spawns = 0;

        final int spawnLimit = rulesSettings.getSpawnLimit();
        for (int i = this.creatures.size(); i < spawnLimit; i++) {
            this.spawnCreature();
        }

        final int extraSpawns = rulesSettings.getExtraSpawns();
        for (int i = 0; i < extraSpawns; i++) {
            this.spawnCreature();
        }

        for(final Creature creature:  this.creatures) {
            creature.calculateNextStep(this.map);
            this.calculateEnergyPenalty(creature, rulesSettings);
            if (creature.isDead()) {
                this.creatureFactory.disassembleCreature(creature);
                this.creatures.remove(creature);
            }

            final double birthEnergy = rulesSettings.getBirthEnergy();
            if (creature.getWantToGiveBirth() > 0 && creature.getEnergy() > birthEnergy) {
                final Creature child = this.creatureFactory.cloneCreature(creature);
                creature.reduceEnergy(birthEnergy);
                this.creatures.add(child);
            }
        }
    }

    private void calculateEnergyPenalty(final Creature creature, final RulesSettings rulesSettings) {
        // TODO maybe feeler length as soon as feeler length are growable
        final double energyLoss = rulesSettings.getRoundEnergyLoss();
        final double eatEnergyLoss = rulesSettings.getEatEnergyLoss();
        final double speedEnergyLoss = rulesSettings.getSpeedEnergyLoss();
        final double ageImpact = rulesSettings.getAgeImpactFactor();

        double penalty;
        penalty = energyLoss + creature.getWantToEat() * eatEnergyLoss + Math.abs(creature.getSpeed()) * speedEnergyLoss;
        penalty = penalty * ( 1 + creature.getAge() * ageImpact );
        creature.reduceEnergy(penalty);
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
