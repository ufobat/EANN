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

    // Neuronal Network to Creature Stats
    private static final double FOOD_TO_ENERGY = 30f;

    // Energy Panelty
    private static final int ENERGYLOSS = 5;
    private static final double SPEED_IMPACT = 1;
    private static final double WANTTOEAT_IMPACT = 1;
    private static final double AGE_IMPACT = 0.007f;

    // Birth
    public static final double BIRTH_LIMIT = 150;

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

    private NeuronalNetwork brain;
    private Color color;
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

    public void calculateNextStep(final Map map) {
        // build input vector
        this.becomeOlder();
        final double[] inputVector = this.getBrainInputVector(map);
        final double[] outputVector = this.brain.think(inputVector);

        this.setBrainOutputVector(outputVector, 0);
        this.applyWishes(map);

        int feelerBrainPos = Creature.BRAIN_OUT_ARGS;
        for (final Feeler feeler : this.feelers) {
            feeler.setBrainOutputVector(outputVector, feelerBrainPos);
            feelerBrainPos += Feeler.BRAIN_OUT_ARGS;
            feeler.applyWishes();
        }
    }

    private double[] getBrainInputVector(final Map map) {
        double[] brainInputVector = new double[ Creature.BRAIN_IN_ARGS + this.feelers.length * Feeler.BRAIN_IN_ARGS ];
        int index = 0;
        final Tile tile = map.getTileUnderPos(this.posX, this.posY);

        brainInputVector[index++] = this.energy;
        brainInputVector[index++] = this.age;
        brainInputVector[index++] = this.speed;
        brainInputVector[index++] = this.angle;
        brainInputVector[index++] = tile.getFoodLevel();
        brainInputVector[index++] = tile.isWater() ? 1 : 0;
        brainInputVector[index++] = this.hadCollision ? 1 : 0;

        for (final Feeler feeler : this.feelers) {
            final Tile feelerTile = feeler.getCurrentTile(map, this);
            brainInputVector[index++] = feeler.getOcclusion();
            brainInputVector[index++] = feeler.getLength();
            brainInputVector[index++] = feeler.getAngle();
            brainInputVector[index++] = feelerTile.getFoodLevel();
            brainInputVector[index++] = feelerTile.isWater() ? 1 : 0;
        }
        return brainInputVector;
    }

    @SuppressWarnings("PMD.ArrayIsStoredDirectly")
    private void setBrainOutputVector(final double[] brainOutputVector, final int startPos) {
        int index = startPos;
        this.wantToAccelerate = brainOutputVector[index++];
        this.wantToRotate = brainOutputVector[index++];
        this.wantToEat = brainOutputVector[index++];
        this.wantToGiveBirth = brainOutputVector[index++];
    }

    private void applyWishes(final Map map) {
        // Movement of Creature
        this.speed += this.wantToAccelerate;
        final double modulo = (this.angle + this.wantToRotate) % (2 * Math.PI);
        this.angle = modulo < 0 ? modulo + 2 * Math.PI : modulo;
        final int xOffset = (int) (Math.sin(this.angle) * this.speed);
        final int yOffset = (int) (Math.cos(this.angle) * this.speed);

        int newPosX = this.posX + xOffset;
        int newPosY = this.posY + yOffset;
        this.hadCollision = false;

        final int overallRadius = this.getOverallRadius();
        if (newPosX - overallRadius < 0) {
            this.hadCollision = true;
            newPosX = overallRadius;
        } else if (newPosX + overallRadius + 1> map.getWidth()) {
            this.hadCollision = true;
            newPosX = map.getWidth() - overallRadius - 1;
        }

        if (newPosY - overallRadius < 0) {
            this.hadCollision = true;
            newPosY = overallRadius;
        } else if (newPosY + overallRadius + 1 > map.getLength()) {
            this.hadCollision = true;
            newPosY = map.getLength() - overallRadius - 1 ;
        }
        this.posX = newPosX;
        this.posY = newPosY;

        // Eat
        if (this.wantToEat > 0) {
            final Tile tile = map.getTileUnderPos(this.posX, this.posY);
            final double ate = tile.reduceFoodLevel(this.wantToEat);
            final double ateEnergyLevel = ate * Creature.FOOD_TO_ENERGY;
            // System.out.printf("Creature %s: ate %s\n", this.hashCode(), ateEnergyLevel);
            this.energy += ateEnergyLevel;

        }
        final double energyPenalty = this.calulateEnergyPanelty();
        // double oldEnergy = this.energy;
        this.reduceEnergy(energyPenalty);
        // System.out.printf("Creature %s at %s, %s\n", this.hashCode(), this.posX, this.posY);
        // System.out.printf("Creature %s: %s - %s => %s\n", this.hashCode(), oldEnergy, energyPenalty, this.energy);
        // System.out.println();
    }

    public void reduceEnergy(final double energyPenalty) {
         this.energy -= energyPenalty;
    }

    public int getOverallRadius() {
        int radius = this.bodyRadius;
        for (final Feeler feeler : this.feelers) {
            radius = Math.max(feeler.getLength(), radius);
        }
        return radius;
    }

    private double calulateEnergyPanelty() {
        // FIXME reasonable factors for wantToEat and speed
        // TODO maybe feeler length as soon as feeler length are growable
        double panelty = Creature.ENERGYLOSS + Creature.WANTTOEAT_IMPACT * this.wantToEat + Creature.SPEED_IMPACT * Math.abs(this.speed);
        panelty = (1 + this.age * Creature.AGE_IMPACT) * panelty;
        return panelty;
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
    public boolean canGiveBirth() {
         return this.wantToGiveBirth > 0 && this.energy > Creature.BIRTH_LIMIT;
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

    public void setBrain(final NeuronalNetwork brain) {
        this.brain = brain;
    }

    @Override
    public int compareTo(final Creature o) {
        return Integer.compare(this.hashCode(), o.hashCode());
    }

    public void setColor(final Color color) {
        this.color = color;
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
}
