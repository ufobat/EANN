package org.eann.sim.simulation;

import org.eann.sim.configuration.CreatureSettings;
import org.eann.sim.simulation.creature.Creature;
import org.eann.sim.simulation.creature.CreatureFactory;
import org.eann.sim.simulation.creature.CreatureState;
import org.eann.sim.simulation.creature.FamilyRegister;

import java.util.Random;

/**
 * Created by martin on 14.04.17.
 */
final public class WorldCreatureUtils {

    private static Random randomGenerator = new Random();

    private WorldCreatureUtils() {
        /* never called */
    }

    @SuppressWarnings({"PMD.ProhibitPublicStaticMethods", "PMD.AvoidDuplicateLiterals"})
    static public void spawnCreature(final World world, final CreatureFactory factory, final CreatureSettings settings) {
        final Creature creature = factory.buildCreature(settings);
        final int creatureSize = settings.getCreatureSize();
        final int posX = randomGenerator.nextInt(world.getMap().getWidth() - creatureSize * 2 - 1) + creatureSize;
        final int posY = randomGenerator.nextInt(world.getMap().getLength() - creatureSize * 2 - 1) + creatureSize;
        final CreatureState state = creature.getState();
        final FamilyRegister register = creature.getRegister();
        register.setBirthDate(world.getDate());
        state.setPosX(posX);
        state.setPosY(posY);
        world.addCreature(creature);
    }

    @SuppressWarnings({"PMD.ProhibitPublicStaticMethods", "PMD.AvoidDuplicateLiterals"})
    static public void cloneCreature(final World world, final CreatureFactory factory, final Creature creature, final double birthEnergy) {
        final Creature child = factory.cloneCreature(creature);
        final CreatureState state = creature.getState();
        state.reduceEnergy(birthEnergy);
        child.getState().setEnergy(birthEnergy);
        world.addCreature(child);
    }

    @SuppressWarnings({"PMD.ProhibitPublicStaticMethods", "PMD.AvoidDuplicateLiterals"})
    static public void removeCreature(final World world, final CreatureFactory factory, final Creature creature) {
        creature.getRegister().setDeathDate(world.getDate());
        factory.disassembleCreature(creature);
        world.removeCreature(creature);
    }
}
