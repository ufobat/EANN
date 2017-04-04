package org.eann.sim.simulation;

import org.eann.sim.configuration.CreatureSettings;
import org.eann.sim.configuration.FeelerSettings;
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

    public CreatureFactory() {
        this.randomGenerator = new Random();
        this.colorManager = new ColorManager();
    }

    @SuppressWarnings({"PMD.AvoidInstantiatingObjectsInLoops", "PMD.AvoidDuplicateLiterals"})
    public Creature buildCreature(final World world) {

        final CreatureSettings settings = world.getCreatureSettings();
        final int creatureSize = settings.getCreatureSize();
        final int posX = this.randomGenerator.nextInt(world.getMap().getWidth() - creatureSize * 2 - 1) + creatureSize;
        final int posY = this.randomGenerator.nextInt(world.getMap().getLength() - creatureSize * 2 - 1) + creatureSize;

        final FeelerSettings[] feelerSettings = settings.getFeelerSettings();
        final Feeler[] feeler = new Feeler[feelerSettings.length];
        for (int i = 0; i <feelerSettings.length; i++) {
            feeler[i] = new Feeler(feelerSettings[i].getLength(), 0);
        }

        int[] hiddenLayer = new int[settings.getNoOfHiddenLayer()];
        for (int i = 0; i < hiddenLayer.length ; i++) {
            hiddenLayer[i] = settings.getNeuronsPerHiddenLayer();
        }

        final NeuronalNetwork newBrain = new NeuronalNetwork(
                Creature.BRAIN_IN_ARGS + feeler.length * Feeler.BRAIN_IN_ARGS,
                Creature.BRAIN_OUT_ARGS + feeler.length * Feeler.BRAIN_OUT_ARGS,
                hiddenLayer
        );

        final Color color = this.colorManager.nextColor();
        return new Creature(posX, posY, settings.getBodyRadius(), settings.getStartEnergy(), 0, 0, 0, color, UUID.randomUUID(), newBrain, feeler);

    }

    @SuppressWarnings({"PMD.AvoidInstantiatingObjectsInLoops", "PMD.AvoidDuplicateLiterals"})
    public Creature cloneCreature(final Creature parent) {
        final NeuronalNetwork newBrain = parent.getBrain().getMutation();
        parent.reduceEnergy(Creature.BIRTH_LIMIT);
        final Feeler[] parentFeeler = parent.getFeelers();
        final Feeler[] cloneFeeler = new Feeler[parentFeeler.length];
        for (int i = 0; i < parentFeeler.length; i++) {
            cloneFeeler[i] = new Feeler(parentFeeler[i].getLength(), parentFeeler[i].getAngle());
        }
        final Color color = parent.getColor();
        this.colorManager.incColor(color);
        return new Creature(parent.getPosX(), parent.getPosY(), 5, 100, 0, 0, 0, color, UUID.randomUUID(), newBrain, cloneFeeler);

    }

    public void disassembleCreature(final Creature creature) {
        final Color color = creature.getColor();
        this.colorManager.decColor(color);
    }
}
