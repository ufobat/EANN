package org.eann.sim.simulation.creature;

import java.awt.Color;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by martin on 18.03.17.
 */
@SuppressWarnings("PMD.TooManyFields")
public class CreatureState implements Serializable {

    // Neuronal Network

    private static final long serialVersionUID = -2074328681850165235L;

    // information about me
    private int posX;
    private int posY;
    final private int bodyRadius;
    private int age;
    private double energy;
    private double angle;
    private double speed;
    final private FeelerState[] feelerStates;
    private boolean hadCollision;

    private final Color color;
    private final UUID uuid;

    @SuppressWarnings("PMD.ExcessiveParameterList")
    public CreatureState(final int posX, final int posY, final int bodyRadius, final double energy, final double angle, final double speed, final int age, final Color color, final UUID uuid, final FeelerState... feelerStates) {
        this.posX = posX;
        this.posY = posY;
        this.bodyRadius = bodyRadius;
        this.energy = energy;
        this.angle = angle;
        this.speed = speed;
        this.age = age;
        this.color = color;
        this.uuid = uuid;
        this.feelerStates = feelerStates;
    }

    public CreatureState(final CreatureState s) {
        this(s.posX, s.posY, s.bodyRadius, s.energy, s.angle, s.speed, s.age, s.color, s.uuid, s.getCloneFeelerStates());
    }

    public int getOverallRadius() {
        int radius = this.bodyRadius;
        for (final FeelerState feelerState : this.feelerStates) {
            radius = Math.max(feelerState.getLength(), radius);
        }
        return radius;
    }

    public void becomeOlder() {
        this.age += 1;
    }

    public double accelerate(final double wantToAccelerate) {
        this.speed = this.speed + wantToAccelerate;
        return this.speed;
    }

    public int getPosX() {
        return this.posX;
    }

    public void setPosX(final int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public void setPosY(final int posY) {
        this.posY = posY;
    }

    public int getBodyRadius() {
        return this.bodyRadius;
    }

    @SuppressWarnings("PMD.MethodReturnsInternalArray")
    public FeelerState[] getFeelerStates() {
        return this.feelerStates;
    }

    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    private FeelerState[] getCloneFeelerStates() {
        final FeelerState[] feelerStates = new FeelerState[this.feelerStates.length];
        int i = 0;
        for (final FeelerState state: this.feelerStates) {
            feelerStates[i++] = new FeelerState(state);
        }
        return feelerStates;
    }

    public Color getColor() {
        return color;
    }

    public UUID getUuid() {
        return uuid;
    }

    public double getSpeed() {
        return this.speed;
    }

    public int getAge() {
        return this.age;
    }

    public double getAngle() {
        return this.angle;
    }

    public void setAngle(final double angle) {
        this.angle = angle;
    }

    public void setHadCollision(final boolean hadCollision) {
        this.hadCollision = hadCollision;
    }

    public boolean isHadCollision() {
        return this.hadCollision;
    }

    public void reduceEnergy(final double penalty) {
        this.energy = this.energy - penalty;
    }

    public void increaseEnergy(final double ateEnergyLevel) {
        this.energy = this.energy + ateEnergyLevel;
    }

    public void setEnergy(final double energy) {
        this.energy = energy;
    }

    public double getEnergy() {
        return this.energy;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public void setSpeed(final double speed) {
        this.speed = speed;
    }
}
