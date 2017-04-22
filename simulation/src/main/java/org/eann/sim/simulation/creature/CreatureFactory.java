package org.eann.sim.simulation.creature;

import org.eann.sim.configuration.CreatureSettings;
import org.eann.sim.simulation.neuronalnet.NeuronalNetwork;
import org.eann.sim.simulation.neuronalnet.NeuronalNetworkFactory;

/**
 * Created by martin on 03.04.17.
 */
public class CreatureFactory {

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

        final NeuronalNetworkFactory factory = new NeuronalNetworkFactory();
        final int noInNeurons = CreatureSensors.BRAIN_IN_ARGS + feelerState.length * FeelerSensors.BRAIN_IN_ARGS;
        final int noOutNeurons = CreatureControls.BRAIN_OUT_ARGS + feelerState.length * FeelerControls.BRAIN_OUT_ARGS;
        final NeuronalNetwork newBrain = factory.buildBrain(noInNeurons, noOutNeurons);

        final double speedFactor = settings.getSpeedFactor();
        final CreatureState newState = new CreatureState(0, 0, settings.getBodyRadius(), settings.getStartEnergy(), 0, 0, 0, speedFactor, feelerState);
        final FeelerSensors newFeelerSensors = new FeelerSensors();
        final CreatureSensors newSensors = new CreatureSensors(newFeelerSensors);
        final FeelerControls newFeelerControls = new FeelerControls();
        final CreatureControls newControls = new CreatureControls(newFeelerControls);
        final FamilyRegister register = new FamilyRegister();

        return new Creature(newState, newSensors, newBrain, newControls, register);
    }

    @SuppressWarnings({"PMD.AvoidInstantiatingObjectsInLoops", "PMD.AvoidDuplicateLiterals"})
    public Creature cloneCreature(final Creature parent) {
        final NeuronalNetwork newBrain = new NeuronalNetwork(parent.getBrain());
        final CreatureSensors newSensors = new CreatureSensors(parent.getSensors());
        final CreatureControls newControls = new CreatureControls(parent.getControls());
        final CreatureState newState = new CreatureState(parent.getState());
        final FamilyRegister parentRegister = parent.getRegister();
        final FamilyRegister newRegister = new FamilyRegister(parentRegister.getTribe(), parentRegister.getSelf());

        parentRegister.appendChild(newRegister.getSelf());
        newState.setAge(0);
        newState.setAngle(0);
        newState.setSpeed(0);

        return new Creature(newState, newSensors, newBrain, newControls, newRegister );
    }
}
