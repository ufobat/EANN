package org.eann.sim.simulation;

import org.eann.sim.simulation.neuronalnet.NeuronalNetwork;
import java.awt.*;
import java.util.UUID;

/**
 * Created by martin on 18.03.17.
 */
@SuppressWarnings("PMD.TooManyFields")
public class Creature implements Comparable<Creature> {
    // Neuronal Network
    public static final int BRAIN_IN_ARGS = 7;
    public static final int BRAIN_OUT_ARGS = 4;

    // information about me
    private int posX;
    private int posY;
    final private int bodyRadius;
    private int age;
    private double energy;
    private double angle;
    private double speed;
    final private Feeler[] feelers;
    private boolean hadCollision;

    // things my brain wants me to do
    private double wantToEat;
    private double wantToAccelerate;
    private double wantToRotate;
    private double wantToGiveBirth;

    private final NeuronalNetwork brain;
    private final Color color;
    private final UUID uuid;

    @SuppressWarnings("PMD.ExcessiveParameterList")
    public Creature(final int posX, final int posY, final int bodyRadius, final double energy, final double angle, final double speed, final int age, final Color color, final UUID uuid, final NeuronalNetwork brain, final Feeler... feelers) {
        this.posX = posX;
        this.posY = posY;
        this.bodyRadius = bodyRadius;
        this.energy = energy;
        this.feelers = feelers;
        this.angle = angle;
        this.speed = speed;
        this.age = age;
        this.color = color;
        this.uuid = uuid;
        this.brain = brain;
    }

    @SuppressWarnings("PMD.ArrayIsStoredDirectly")
    public void setBrainOutputVector(final double[] brainOutputVector, final int startPos) {
        int index = startPos;
        this.wantToAccelerate = brainOutputVector[index++];
        this.wantToRotate = brainOutputVector[index++];
        this.wantToEat = brainOutputVector[index++];
        this.wantToGiveBirth = brainOutputVector[index++];
    }

    public int getOverallRadius() {
        int radius = this.bodyRadius;
        for (final Feeler feeler : this.feelers) {
            radius = Math.max(feeler.getLength(), radius);
        }
        return radius;
    }

    public void reduceEnergy(final double penalty) {
        this.energy = this.energy - penalty;
    }

    public void becomeOlder() {
        this.age += 1;
    }

    public boolean isDead() {
        final boolean isDead = this.energy < 0;
        //if (isDead)
        //    System.out.printf("Creature %s is dead\n", this.hashCode());
        return isDead;
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
    public Feeler[] getFeelers() {
        return this.feelers;
    }

    @Override
    public int compareTo(final Creature o) {
        return Integer.compare(this.hashCode(), o.hashCode());
    }

    public Color getColor() {
        return color;
    }

    public UUID getUuid() {
        return uuid;
    }

    public NeuronalNetwork getBrain() {
        return brain;
    }

    public double getWantToEat() {
        return this.wantToEat;
    }

    public double getSpeed() {
        return this.speed;
    }

    public int getAge() {
        return this.age;
    }

    public double getWantToGiveBirth() {
        return this.wantToGiveBirth;
    }

    public double getEnergy() {
        return this.energy;
    }

    public double getWantToAccelerate() {
        return this.wantToAccelerate;
    }

    public double getAngle() {
        return this.angle;
    }

    public double getWantToRotate() {
        return this.wantToRotate;
    }

    public void setAngle(final double angle) {
        this.angle = angle;
    }

    public void setHadCollision(final boolean hadCollision) {
        this.hadCollision = hadCollision;
    }

    public void increaseEnergy(final double ateEnergyLevel) {
        this.energy = this.energy + ateEnergyLevel;
    }

    public boolean isHadCollision() {
        return this.hadCollision;
    }

    public void setEnergy(final double energy) {
        this.energy = energy;
    }
}
