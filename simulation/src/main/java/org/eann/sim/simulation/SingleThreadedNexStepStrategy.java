package org.eann.sim.simulation;

import org.eann.sim.configuration.Config;
import org.eann.sim.configuration.CreatureSettings;
import org.eann.sim.configuration.RulesSettings;
import org.eann.sim.simulation.creature.*;
import java.util.Set;

/**
 * Created by martin on 11.04.17.
 */
@SuppressWarnings({"PMD.ModifiedCyclomaticComplexity", "PMD.AvoidDuplicateLiterals"})
public class SingleThreadedNexStepStrategy implements NexStepStrategy {
    private final CreatureFactory creatureFactory;

    public SingleThreadedNexStepStrategy() {
        this.creatureFactory = new CreatureFactory();
    }

    @Override
    public void calculateNextStep(final World world, final Config config) {
        final RulesSettings rulesSettings = config.getRulesSettings();
        this.mapNextStep(world, rulesSettings);
        this.creaturesNextStep(world, config);
        world.increaseDate();
        world.takeSnapshot();
    }

    private void creaturesNextStep(final World world, final Config config) {
        final RulesSettings rulesSettings = config.getRulesSettings();
        final CreatureSettings creatureSettings= config.getCreatureSettings();
        final Set<Creature> creatures = world.getCreatures();
        final int spawnLimit = rulesSettings.getSpawnLimit();

        int spawns = 0;
        for (int i = creatures.size(); i < spawnLimit; i++) {
            WorldCreatureUtils.spawnCreature(world, this.creatureFactory, creatureSettings);
            spawns++;
        }

        final int extraSpawns = rulesSettings.getExtraSpawns();
        for (int i = 0; i < extraSpawns; i++) {
            WorldCreatureUtils.spawnCreature(world, this.creatureFactory, creatureSettings);
            spawns++;
        }

        for(final Creature creature : creatures) {
            this.creatureNextStep(world, creature, rulesSettings);
        }

        world.setSpawns(spawns);
    }

    @SuppressWarnings({"PMD.NPathComplexity", "PMD.ExcessiveMethodLength", "PMD.CyclomaticComplexity", "PMD.StdCyclomaticComplexity", "PMD.ModifiedCyclomaticComplexity"})
    private void creatureNextStep(final World world, final Creature creature, final RulesSettings rulesSettings) {
        final CreatureState state = creature.getState();
        final CreatureSensors sensors = creature.getSensors();
        final Map map = world.getMap();
        final FeelerState[] feelerStates = state.getFeelerStates();

        // Brain input Vector
        final int creatureX = state.getPosX();
        final int creatureY = state.getPosY();
        final Tile tile = map.getTileUnderPos(creatureX, creatureY);

        sensors.setEnergy(state.getEnergy());
        sensors.setAge(state.getAge());
        sensors.setSpeed(state.getSpeed());
        sensors.setAngle(state.getAngle());
        sensors.setFoodLevel(tile.getFoodLevel());
        sensors.setWater(tile.isWater() ? 1 : -1);
        sensors.setHeight(tile.getHeight());
        sensors.setHadCollision(state.isHadCollision() ? 1 : -1);

        for (int i = 0; i < feelerStates.length; i++) {
            final FeelerState feelerState = feelerStates[i];
            final int feelerX = feelerState.getSensorPosX(creatureX);
            final int feelerY = feelerState.getSensorPosY(creatureY);
            final Tile feelerTile = map.getTileUnderPos(feelerX, feelerY);
            final FeelerSensors feelerSensor = sensors.getFeelerSensor(i);
            feelerSensor.setOcclusion(feelerState.getOcclusion());
            feelerSensor.setLength(feelerState.getLength());
            feelerSensor.setAngle(feelerState.getAngle());
            feelerSensor.setFoodLevel(feelerTile.getFoodLevel());
            feelerSensor.setWater(feelerTile.isWater() ? 1 : -1);
            feelerSensor.setHeight(feelerTile.getHeight());
        }

        state.becomeOlder();

        final CreatureControls controls = creature.think();


        // Eat before movement!!!!
        final double wantToEat = controls.getWantToEat();
        if (wantToEat > 0) {
            final double ate = tile.reduceFoodLevel(wantToEat);
            final double ateEnergyLevel = ate * rulesSettings.getFoodToEnergy();
            // System.out.printf("CreatureState %s: ate %s\n", this.hashCode(), ateEnergyLevel);
            state.increaseEnergy(ateEnergyLevel);
        }

        // Movement of CreatureState
        final double wantToAccelerate = controls.getWantToAccelerate();
        final double wantToRotate = controls.getWantToRotate();
        final double speed = state.accelerate(wantToAccelerate);
        final double modulo = (state.getAngle() + wantToRotate) % (2 * Math.PI);
        final double angle = modulo < 0 ? modulo + 2 * Math.PI : modulo;
        state.setAngle(angle);
        final int xOffset = (int) (Math.sin(angle) * speed);
        final int yOffset = (int) (Math.cos(angle) * speed);

        int newPosX = state.getPosX() + xOffset;
        int newPosY = state.getPosY() + yOffset;
        state.setHadCollision(false);

        final int overallRadius = state.getOverallRadius();
        if (newPosX - overallRadius < 0) {
            state.setHadCollision(true);
            newPosX = overallRadius;
        } else if (newPosX + overallRadius + 1> map.getWidth()) {
            state.setHadCollision(true);
            newPosX = map.getWidth() - overallRadius - 1;
        }

        if (newPosY - overallRadius < 0) {
            state.setHadCollision(true);
            newPosY = overallRadius;
        } else if (newPosY + overallRadius + 1 > map.getLength()) {
            state.setHadCollision(true);
            newPosY = map.getLength() - overallRadius - 1 ;
        }
        state.setPosX(newPosX);
        state.setPosY(newPosY);

        // calculate energy penalty
        final double energyLoss = rulesSettings.getRoundEnergyLoss();
        final double eatEnergyLoss = rulesSettings.getEatEnergyLoss();
        final double speedEnergyLoss = rulesSettings.getSpeedEnergyLoss();
        final double ageImpact = rulesSettings.getAgeImpactFactor();

        double penalty;
        penalty = energyLoss + wantToEat * eatEnergyLoss + Math.abs(state.getSpeed()) * speedEnergyLoss;
        penalty = penalty * ( 1 + state.getAge() * ageImpact );
        state.reduceEnergy(penalty);

        // FeelerState Movement
        for (int i = 0; i < feelerStates.length; i++) {
            final FeelerState feelerState = feelerStates[i];
            final FeelerControls feelerControls = controls.getFeelerControls(i);
            final double feelerModulo = (feelerState.getAngle() + feelerControls.getWantToRotate()) % (2 * Math.PI);
            final double feelerAngle = feelerModulo < 0 ? feelerModulo + 2 * Math.PI : feelerModulo;
            feelerState.setAngle(feelerAngle);
        }

        // Birth of new Children
        final double wantToGiveBirth = controls.getWantToGiveBirth();
        final double birthEnergy = rulesSettings.getBirthEnergy();
        if (wantToGiveBirth > 0 && state.getEnergy() > birthEnergy) {
            WorldCreatureUtils.cloneCreature(world, this.creatureFactory, creature, birthEnergy);
        }

        if (creature.isDead()) {
            WorldCreatureUtils.removeCreature(world, this.creatureFactory, creature);
        }
    }

    private void mapNextStep(final World world, final RulesSettings rulesSettings) {
        final Map map = world.getMap();
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
}
