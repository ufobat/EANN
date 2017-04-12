package org.eann.sim.simulation;

import org.eann.sim.configuration.Config;
import org.eann.sim.simulation.creature.Creature;
import org.eann.sim.simulation.creature.CreatureFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * Created by martin on 19.03.17.
 */
public class CreatureStateFactoryTest {

    private World world;
    private Config config;

    @BeforeEach
    public void buildWorld() {
        this.config = new Config();
    }

    @Test
    public void construction() {
        final CreatureFactory creatureFactory = new CreatureFactory();
        final Creature creature = creatureFactory.buildCreature(this.config.getCreatureSettings());
        assertNotNull(creature, "built a creatureState");
    }


    @Test
    public void cloning() {
        final CreatureFactory creatureFactory = new CreatureFactory();
        final Creature creatureState = creatureFactory.buildCreature(this.config.getCreatureSettings());
        final Creature clone    = creatureFactory.cloneCreature(creatureState);
        assertNotNull(clone, "built a creatureState");
    }


    @Test
    public void disassembling() {
        final CreatureFactory creatureFactory = new CreatureFactory();
        final Creature creatureState = creatureFactory.buildCreature(this.config.getCreatureSettings());
        creatureFactory.disassembleCreature(creatureState);
    }

}
