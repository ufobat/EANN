package org.eann.sim.simulation;

import org.eann.sim.simulation.neuronalnet.NeuronalNetwork;

import java.awt.*;
import java.util.Random;
import java.util.UUID;

/**
 * Created by martin on 03.04.17.
 */
public class CreatureFactory {

    final private Random randomGenerator;
    final private ColorManager colorManager;

    CreatureFactory() {
        this.randomGenerator = new Random();
        this.colorManager = new ColorManager();
    }


    public Creature buildCreature(final World world) {
        // 10 == feelersize
        // 5  == bodysize
        final int creatureSize = 10 + 5;
        final int posX = this.randomGenerator.nextInt(world.getMap().getWidth() - creatureSize * 2 - 1) + creatureSize;
        final int posY = this.randomGenerator.nextInt(world.getMap().getLength() - creatureSize * 2 - 1) + creatureSize;

        final NeuronalNetwork newBrain = new NeuronalNetwork(
                Creature.BRAIN_IN_ARGS + Feeler.BRAIN_IN_ARGS,
                Creature.BRAIN_OUT_ARGS + Feeler.BRAIN_OUT_ARGS,
                Creature.BRAIN_IN_ARGS + Feeler.BRAIN_IN_ARGS
        );
        final Feeler feeler = new Feeler(10, 0);
        final Color color = this.colorManager.nextColor();
        return new Creature(posX, posY, 5, 100, 0, 0, 0, color, UUID.randomUUID(), newBrain, feeler);
    }

    public Creature cloneCreature(final Creature parent) {
        final NeuronalNetwork newBrain = parent.getBrain().getMutation();
        parent.reduceEnergy(Creature.BIRTH_LIMIT);

        final Feeler feeler = new Feeler(10, 0);
        final Color color = parent.getColor();
        this.colorManager.incColor(color);
        return new Creature(parent.getPosX(), parent.getPosY(), 5, 100, 0, 0, 0, color, UUID.randomUUID(), newBrain, feeler);

    }

    public void disassembleCreature(final Creature creature) {
        final Color color = creature.getColor();
        this.colorManager.decColor(color);
    }
}
