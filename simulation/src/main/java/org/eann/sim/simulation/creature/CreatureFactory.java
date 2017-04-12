package org.eann.sim.simulation.creature;

import org.eann.sim.configuration.CreatureSettings;
import org.eann.sim.simulation.neuronalnet.NeuronalNetwork;

import java.awt.*;
import java.util.UUID;

/**
 * Created by martin on 03.04.17.
 */
public class CreatureFactory {

    final private ColorManager colorManager;

    public CreatureFactory() {
        this.colorManager = new ColorManager();
    }

    @SuppressWarnings({"PMD.AvoidInstantiatingObjectsInLoops", "PMD.AvoidDuplicateLiterals"})
    public Creature buildCreature(final CreatureSettings settings) {

        final FeelerState[] feelerState = new FeelerState[settings.getNoOfFeeler()];
        for (int i = 0; i < feelerState.length; i++) {
            feelerState[i] = new FeelerState(settings.getFeelerLength(), 0, 0);
        }

        int[] hiddenLayer = new int[settings.getNoOfHiddenLayer()];
        for (int i = 0; i < hiddenLayer.length ; i++) {
            hiddenLayer[i] = settings.getNeuronsPerHiddenLayer();
        }

        final NeuronalNetwork newBrain = new NeuronalNetwork(
                CreatureSensors.BRAIN_IN_ARGS + feelerState.length * FeelerSensors.BRAIN_IN_ARGS,
                CreatureControls.BRAIN_OUT_ARGS + feelerState.length * FeelerControls.BRAIN_OUT_ARGS,
                hiddenLayer
        );

        final Color color = this.colorManager.nextColor();
        final CreatureState newState = new CreatureState(0, 0, settings.getBodyRadius(), settings.getStartEnergy(), 0, 0, 0, color, UUID.randomUUID(), feelerState);
        final FeelerSensors newFeelerSensors = new FeelerSensors();
        final CreatureSensors newSensors = new CreatureSensors(newFeelerSensors);
        final FeelerControls newFeelerControls = new FeelerControls();
        final CreatureControls newControls = new CreatureControls(newFeelerControls);

        return new Creature(newState, newSensors, newBrain, newControls);
    }

    @SuppressWarnings({"PMD.AvoidInstantiatingObjectsInLoops", "PMD.AvoidDuplicateLiterals"})
    public Creature cloneCreature(final Creature parent) {
        final CreatureState parentState = parent.getState();
        final NeuronalNetwork newBrain = parent.getBrain().getMutation();
        final CreatureSensors newSensors = new CreatureSensors(parent.getSensors());
        final CreatureControls newControls = new CreatureControls(parent.getControls());
        final Color color = parentState.getColor();
        final CreatureState newState = new CreatureState(parentState);

        newState.setAge(0);
        newState.setAngle(0);
        newState.setSpeed(0);
        this.colorManager.incColor(color);

        return new Creature(newState, newSensors, newBrain, newControls);
    }

    public void disassembleCreature(final Creature creature) {
        final Color color = creature.getState().getColor();
        this.colorManager.decColor(color);
    }
}