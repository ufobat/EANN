package org.eann.sim.simulation.dataexchange;

import org.eann.sim.simulation.creature.Creature;
import org.eann.sim.simulation.creature.CreatureState;
import org.eann.sim.simulation.creature.FamilyRegister;
import java.io.Serializable;

/**
 * Created by martin on 14.04.17.
 */
public class CreatureBean implements Serializable {
    private static final long serialVersionUID = 1489794032579382652L;
    private final CreatureState state;
    private final FamilyRegister register;

    public CreatureBean(final CreatureState state, final FamilyRegister register) {
        this.state = state;
        this.register = register;
    }

    public CreatureBean(final Creature creature) {
        this(new CreatureState(creature.getState()), new FamilyRegister(creature.getRegister()));
    }

    public CreatureState getState() {
        return this.state;
    }

    public FamilyRegister getRegister() {
        return this.register;
    }
}
