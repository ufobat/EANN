package org.eann.sim.simulation.creature;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by martin on 12.04.17.
 */
public class FamilyRegister {
    private final UUID self;
    private final UUID tribe;
    private final UUID parent;
    private long birthDate;
    private long deathDate;
    private final Set<UUID> children;

    public FamilyRegister(final UUID tribe, final UUID parent, final UUID self) {
        this.tribe = tribe;
        this.parent = parent;
        this.self = self;
        this.children = new HashSet();
    }

    public FamilyRegister(final UUID tribe, final UUID parent) {
        this(tribe, parent, UUID.randomUUID());
    }

    public FamilyRegister() {
        this(UUID.randomUUID(), null, UUID.randomUUID());
    }

    public void appendChild(final UUID child) {
        this.children.add(child);
    }

    public UUID getTribe() {
        return this.tribe;
    }

    public UUID getSelf() {
        return this.self;
    }

    public void setBirthDate(final long birthDate) {
        this.birthDate = birthDate;
    }

    public void setDeathDate(final long deathDate) {
        this.deathDate = deathDate;
    }

    public UUID getParent() {
        return this.parent;
    }

    public long getBirthDate() {
        return this.birthDate;
    }

    public long getDeathDate() {
        return this.deathDate;
    }

}
