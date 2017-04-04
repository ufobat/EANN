package org.eann.sim.simulation;

import org.eann.sim.configuration.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * Created by martin on 19.03.17.
 */
public class CreatureFactoryTest {

    private World world;

    @BeforeEach
    public void buildWorld() {
        final Config config = new Config();
        final WorldFactory worldFactory = new WorldFactory(config.getWorldSettings());
        this.world = worldFactory.buildWorld();
    }

    @Test
    public void construction() {
        final CreatureFactory creatureFactory = new CreatureFactory();
        final Creature creature = creatureFactory.buildCreature(this.world);
        assertNotNull(creature, "built a creature");
    }


    @Test
    public void cloning() {
        final CreatureFactory creatureFactory = new CreatureFactory();
        final Creature creature = creatureFactory.buildCreature(this.world);
        final Creature clone    = creatureFactory.cloneCreature(creature);
        assertNotNull(clone, "built a creature");
    }


    @Test
    public void disassembling() {
        final CreatureFactory creatureFactory = new CreatureFactory();
        final Creature creature = creatureFactory.buildCreature(this.world);
        creatureFactory.disassembleCreature(creature);
    }

}
