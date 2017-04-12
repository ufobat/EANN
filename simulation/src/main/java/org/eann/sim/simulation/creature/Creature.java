package org.eann.sim.simulation.creature;

import org.eann.sim.simulation.neuronalnet.NeuronalNetwork;

/**
 * Created by martin on 12.04.17.
 */
public class Creature implements Comparable<Creature> {
    private final CreatureState state;
    private final CreatureSensors sensors;
    private final NeuronalNetwork brain;
    private final CreatureControls controls;

    public Creature(final CreatureState state, final CreatureSensors sensors, final NeuronalNetwork brain, final CreatureControls controls) {
        this.state = state;
        this.sensors = sensors;
        this.brain = brain;
        this.controls = controls;
    }

    public CreatureState getState() {
        return this.state;
    }

    public CreatureSensors getSensors() {
        return this.sensors;
    }

    public CreatureControls think() {
        final double[] intputVector = this.sensors.toInputVector();
        final double[] outputVector = this.brain.think(intputVector);
        this.controls.fromOutputVector(outputVector);
        return this.controls;
    }

    public boolean isDead() {
        return this.state.getEnergy() <= 0;
    }

    public NeuronalNetwork getBrain() {
        return this.brain;
    }

    public CreatureControls getControls() {
        return this.controls;
    }

    @Override
    public int compareTo(final Creature creature) {
        return Integer.compare(this.hashCode(), creature.hashCode());
    }
}
