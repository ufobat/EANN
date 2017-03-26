package org.eann.sim.simulation;

import org.eann.sim.simulation.neuronalnet.NeuronalNetwork;

/**
 * Created by martin on 18.03.17.
 */
public class Creature implements Comparable<Creature> {
    // Neuronal Network
    private static final int NO_OF_BRAIN_IN_ARGS = 7;
    private static final int NO_OF_BRAIN_OUT_ARGS = 4;

    // Neuronal Network to Creature Stats
    private static final double FOOD_TO_ENERGY_FACTOR = 30f;

    // Energy Panelty
    private static final int ENERGY_LOSS_PER_ROUND = 5;
    private static final double SPEED_IMPACT_FACTOR = 1;
    private static final double WANT_TO_EAT_IMPACT_FACTOR = 1;
    private static final double AGE_IMPACT_FACTOR = 0.007f;

    // Birth
    private static final double ENERGY_BIRTH_LIMIT = 150;

    // information about me
    private int posX;
    private int posY;
    private int bodyRadius;
    private int age;
    private double energy;
    private double angle;
    private double speed;
    private Feeler[] feelers;
    private boolean hadCollision;

    // things my brain wants me to do
    private double wantToEat;
    private double wantToAccelerate;
    private double wantToRotate;
    private double wantToGiveBirth;

    private NeuronalNetwork brain;

    public Creature() {
        this(0, 0);
    }

    public Creature(final int posX, final int posY) {
        this(posX, posY, 5, 100, 0, 0, 0, new Feeler[]{new Feeler(10, 0)});
    }

    public Creature(final int posX, final int posY, final int bodyRadius, final double energy, final double angle, final double speed, final int age, final Feeler[] feelers) {
        this.posX = posX;
        this.posY = posY;
        this.bodyRadius = bodyRadius;
        this.energy = energy;
        this.feelers = feelers;
        this.angle = angle;
        this.speed = speed;
        this.age = age;

        this.brain = new NeuronalNetwork(
                Creature.NO_OF_BRAIN_IN_ARGS + feelers.length * Feeler.NO_OF_BRAIN_IN_ARGS,
                Creature.NO_OF_BRAIN_OUT_ARGS + feelers.length + Feeler.NO_OF_BRAIN_OUT_ARGS,
                1,
                Creature.NO_OF_BRAIN_IN_ARGS + feelers.length * Feeler.NO_OF_BRAIN_IN_ARGS
        );
    }

    public void calculateNextStep(final Map map) {
        // build input vector
        this.becomeOlder();
        double[] inputVector = this.getBrainInputVector(map);
        double[] outputVector = this.brain.think(inputVector);

        this.setBrainOutputVector(outputVector, 0);
        this.applyWishes(map);

        int feelerBrainPos = Creature.NO_OF_BRAIN_IN_ARGS;
        for (Feeler feeler : this.feelers) {
            feeler.setBrainOutputVector(outputVector, feelerBrainPos);
            feelerBrainPos += Feeler.NO_OF_BRAIN_IN_ARGS;
            feeler.applyWishes();
        }
    }

    private double[] getBrainInputVector(final Map map) {
        int noOfBrainInputElements = Creature.NO_OF_BRAIN_IN_ARGS + this.feelers.length * Feeler.NO_OF_BRAIN_IN_ARGS;
        double[] brainInputVector = new double[noOfBrainInputElements];
        int index = 0;
        Tile tile = map.getTileUnderPos(this.posX, this.posY);

        // FIXME - this should be inside the brain.
        brainInputVector[index++] = 1; // bias neuron
        brainInputVector[index++] = this.energy;
        brainInputVector[index++] = this.age;
        brainInputVector[index++] = this.speed;
        brainInputVector[index++] = tile.getFoodLevel();
        brainInputVector[index++] = tile.isWater() ? 1 : 0;
        brainInputVector[index++] = this.hadCollision ? 1 : 0;

        for (Feeler feeler : this.feelers) {
            final Tile feelerTile = feeler.getCurrentTile(map, this);
            brainInputVector[index++] = feeler.getOcclusion();
            brainInputVector[index++] = feeler.getLength();
            brainInputVector[index++] = feeler.getAngle();
            brainInputVector[index++] = feelerTile.getFoodLevel();
            brainInputVector[index++] = feelerTile.isWater() ? 1 : 0;
        }
        return brainInputVector;
    }

    private void setBrainOutputVector(final double[] brainOutputVector, final int startBrainInputPos) {

        // FIXME implement brainOutputVector
        if (brainOutputVector != null) {
            int index = startBrainInputPos;
            this.wantToAccelerate = brainOutputVector[index++];
            this.angle = this.angle + brainOutputVector[index++];
            this.wantToEat = brainOutputVector[index++];
            this.wantToGiveBirth = brainOutputVector[index++];
            for (Feeler feeler : this.feelers) {
                feeler.setWantToRotate(brainOutputVector[index++]);
            }
        }
    }

    private void applyWishes(final Map map) {
        // Movement of Creature
        this.speed += this.wantToAccelerate;
        this.angle += this.wantToRotate;
        int xOffset = (int) (Math.sin(this.angle) * this.speed);
        int yOffset = (int) (Math.cos(this.angle) * this.speed);

        int newPosX = this.posX + xOffset;
        int newPosY = this.posY + yOffset;
        this.hadCollision = false;

        int overallRadius = this.getOverallRadius();
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
            Tile tile = map.getTileUnderPos(this.posX, this.posY);
            double ate = tile.reduceFoodLevel(this.wantToEat);
            double ateEnergyLevel = ate * Creature.FOOD_TO_ENERGY_FACTOR;
            // System.out.printf("Creature %s: ate %s\n", this.hashCode(), ateEnergyLevel);
            this.energy += ateEnergyLevel;

        }
        double energyPenalty = this.calulateEnergyPanelty();
        // double oldEnergy = this.energy;
        this.energy -= energyPenalty;
        // System.out.printf("Creature %s at %s, %s\n", this.hashCode(), this.posX, this.posY);
        // System.out.printf("Creature %s: %s - %s => %s\n", this.hashCode(), oldEnergy, energyPenalty, this.energy);
        // System.out.println();
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
        double panelty = Creature.ENERGY_LOSS_PER_ROUND + Creature.WANT_TO_EAT_IMPACT_FACTOR * this.wantToEat + Creature.SPEED_IMPACT_FACTOR * Math.abs(this.speed);
        panelty = (1 + this.age * Creature.AGE_IMPACT_FACTOR) * panelty;
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

    public Creature giveBirth() {
        Creature child = null;
        if (this.wantToGiveBirth > 0 && this.energy > Creature.ENERGY_BIRTH_LIMIT) {
            System.out.println("BABY TIME");
            this.energy -= ENERGY_BIRTH_LIMIT;
            child = this.cloneAChild();
        }
        return child;
    }

    private Creature cloneAChild() {
        final Creature child = new Creature(this.posX, this.posY);
        child.setBrain(this.brain.getMutation());
        return child;
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

    public Feeler[] getFeelers() {
        return this.feelers;
    }

    public void setBrain(final NeuronalNetwork brain) {
        this.brain = brain;
    }

    @Override
    public int compareTo(Creature o) {
        return Integer.compare(this.hashCode(), o.hashCode());
    }
}
