package org.eann.sim.simulation;

import org.apache.commons.lang3.SerializationUtils;
import org.eann.sim.configuration.Config;
import org.eann.sim.configuration.CreatureSettings;
import org.eann.sim.configuration.RulesSettings;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

public class World {
    final private Map map;
    final private ConcurrentSkipListSet<Creature> creatures;

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
        this.date++;
        this.spawns = 0;

        this.calculateNextStep(this.map, rulesSettings);

        final int spawnLimit = rulesSettings.getSpawnLimit();
        for (int i = this.creatures.size(); i < spawnLimit; i++) {
            this.spawnCreature();
        }

        final int extraSpawns = rulesSettings.getExtraSpawns();
        for (int i = 0; i < extraSpawns; i++) {
            this.spawnCreature();
        }

        for(final Creature creature:  this.creatures) {
            this.calculateNextStep(creature, rulesSettings);
        }
    }
    private void calculateNextStep(final Map map, final RulesSettings rulesSettings) {
        final double growFoodAmount = rulesSettings.getGrowFoodAmount();
        final Tile[][] tiles = map.getTiles();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                final Tile tile = tiles[i][j];

                boolean growMoreFood = false;
                if (! tile.isWater()) {
                    if (tile.isAtMinFood()) {
                        growMoreFood = map.isNeighborFertile(i, j);
                    } else if (tile.isNotAtMaxFood() && tile.isNotAtMinFood()) {
                        growMoreFood = true;
                    }
                }

                if(growMoreFood) {
                    tile.growFood(growFoodAmount);
                }
            }
        }

    }



    private void calculateNextStep(final Creature creature, final RulesSettings rulesSettings) {
        creature.becomeOlder();

        final double[] inputVector = this.getBrainInputVector(creature);
        final double[] outputVector = creature.getBrain().think(inputVector);

        this.setBrainOutputVector(creature, outputVector);
        this.executeNextSteps(creature, rulesSettings);
        this.calculateEnergyPenalty(creature, rulesSettings);

        if (creature.isDead()) {
            this.creatureFactory.disassembleCreature(creature);
            this.creatures.remove(creature);
        }

        final double birthEnergy = rulesSettings.getBirthEnergy();
        if (creature.getWantToGiveBirth() > 0 && creature.getEnergy() > birthEnergy) {
            final Creature child = this.creatureFactory.cloneCreature(creature);
            creature.reduceEnergy(birthEnergy);
            child.setEnergy(rulesSettings.getBirthEnergy());
            this.creatures.add(child);
        }
    }

    public void setBrainOutputVector(final Creature creature, final double... outputVector) {
        creature.setBrainOutputVector(outputVector, 0);
        int feelerBrainPos = Creature.BRAIN_OUT_ARGS;
        for (final Feeler feeler : creature.getFeelers()) {
            feeler.setBrainOutputVector(outputVector, feelerBrainPos);
            feelerBrainPos += Feeler.BRAIN_OUT_ARGS;
        }
    }


    private double[] getBrainInputVector(final Creature creature) {
        double[] brainInputVector = new double[ Creature.BRAIN_IN_ARGS + creature.getFeelers().length * Feeler.BRAIN_IN_ARGS ];
        int index = 0;
        final int creatureX = creature.getPosX();
        final int creatureY = creature.getPosY();
        final Tile tile = this.map.getTileUnderPos(creatureX, creatureY);

        brainInputVector[index++] = creature.getEnergy();
        brainInputVector[index++] = creature.getAge();
        brainInputVector[index++] = creature.getSpeed();
        brainInputVector[index++] = creature.getAngle();
        brainInputVector[index++] = tile.getFoodLevel();
        brainInputVector[index++] = tile.isWater() ? 1 : 0;
        brainInputVector[index++] = creature.isHadCollision() ? 1 : 0;

        for (final Feeler feeler : creature.getFeelers()) {
            final int feelerX = feeler.getSensorPosX(creatureX);
            final int feelerY = feeler.getSensorPosY(creatureY);
            final Tile feelerTile = this.map.getTileUnderPos(feelerX, feelerY);
            brainInputVector[index++] = feeler.getOcclusion();
            brainInputVector[index++] = feeler.getLength();
            brainInputVector[index++] = feeler.getAngle();
            brainInputVector[index++] = feelerTile.getFoodLevel();
            brainInputVector[index++] = feelerTile.isWater() ? 1 : 0;
        }
        return brainInputVector;
    }

    @SuppressWarnings("PMD.NPathComplexity")
    private void executeNextSteps(final Creature creature, final RulesSettings rulesSettings) {
        // Movement of Creature
        final double speed = creature.accelerate(creature.getWantToAccelerate());
        final double modulo = (creature.getAngle() + creature.getWantToRotate()) % (2 * Math.PI);
        final double angle = modulo < 0 ? modulo + 2 * Math.PI : modulo;
        creature.setAngle(angle);
        final int xOffset = (int) (Math.sin(angle) * speed);
        final int yOffset = (int) (Math.cos(angle) * speed);

        int newPosX = creature.getPosX() + xOffset;
        int newPosY = creature.getPosY() + yOffset;
        creature.setHadCollision(false);

        final int overallRadius = creature.getOverallRadius();
        if (newPosX - overallRadius < 0) {
            creature.setHadCollision(true);
            newPosX = overallRadius;
        } else if (newPosX + overallRadius + 1> this.getWidth()) {
            creature.setHadCollision(true);
            newPosX = this.getWidth() - overallRadius - 1;
        }

        if (newPosY - overallRadius < 0) {
            creature.setHadCollision(true);
            newPosY = overallRadius;
        } else if (newPosY + overallRadius + 1 > this.getLength()) {
            creature.setHadCollision(true);
            newPosY = this.getLength() - overallRadius - 1 ;
        }
        creature.setPosX(newPosX);
        creature.setPosY(newPosY);

        // Eat
        final double wantToEat = creature.getWantToEat();
        if (wantToEat > 0) {
            final Tile tile = this.map.getTileUnderPos(newPosX, newPosY);
            final double ate = tile.reduceFoodLevel(wantToEat);
            final double ateEnergyLevel = ate * rulesSettings.getFoodToEnergy();
            // System.out.printf("Creature %s: ate %s\n", this.hashCode(), ateEnergyLevel);
            creature.increaseEnergy(ateEnergyLevel);
        }

        for(final  Feeler feeler: creature.getFeelers()) {
            final double feelerModulo = (feeler.getAngle() + feeler.getWantToRotate()) % (2 * Math.PI);
            final double feelerAngle = feelerModulo < 0 ? feelerModulo + 2 * Math.PI : feelerModulo;
            feeler.setAngle(feelerAngle);
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
        return this.map.getLength();
    }

    public Map getMap() {
        return this.map;
    }

    public Set<Creature> getCreatures() {
        return this.creatures;
    }

    public Map getClonedMap() {
        return SerializationUtils.clone(this.map);
    }

    public Set<Creature> getClonedCreatures() {
        return SerializationUtils.clone(this.creatures);
    }
}
