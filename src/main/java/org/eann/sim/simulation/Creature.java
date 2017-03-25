package org.eann.sim.simulation;

import org.eann.sim.simulation.neuronalnet.NeuronalNetwork;

/**
 * Created by martin on 18.03.17.
 */
public class Creature {
    private static final int NO_OF_BRAIN_IN_ARGS = 6;
    private static final double FOOD_TO_ENERGY_FACTOR = 10f;
    private static final double SPEED_IMPACT_FACTOR = 1;
    private static final double WANT_TO_EAT_IMPACT_FACTOR = 1;
    private static final double AGE_IMPACT_FACTOR = 0.1f;
    private static final int ENERGY_LOSS_PER_ROUND = 5;
    private static final int NO_OF_BRAIN_OUT_ARGS = 4;

    // information about me
    private int posX;
    private int posY;
    private int bodyRadius;
    private int age;
    private double energy;
    private double angle;
    private double speed;
    private Feeler[] feelers;
    private boolean hadColision;

    // things my brain wants me to do
    private double wantToEat;
    private double wantToBeAtSpeed;
    private double wantToRotate;
    private double wantToGiveBirth;
    private NeuronalNetwork brain;

    public Creature() {
        this(0, 0);
    }

    public Creature(int posX, int posY) {
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

        this.brain = new NeuronalNetwork(
                NO_OF_BRAIN_IN_ARGS + feelers.length * Feeler.NO_OF_BRAIN_IN_ARGS,
                NO_OF_BRAIN_OUT_ARGS + feelers.length + Feeler.NO_OF_BRAIN_OUT_ARGS,
                1,
                NO_OF_BRAIN_IN_ARGS + feelers.length * Feeler.NO_OF_BRAIN_IN_ARGS
        );
    }

    public int getPositionX() {
        return posX;
    }

    public int getPositionY() {
        return posY;
    }

    public int getBodyRadius() {
        return bodyRadius;
    }

    public Feeler[] getFeelers() {
        return feelers;
    }

    public void calculateNextStep(final Map map) {
        // build input vector
        this.becomeOlder();
        double[] inputVector = this.getBrainInputVector(map);
        double[] outputVector = this.brain.think(inputVector);

        this.setBrainOutputVector(outputVector, 0);
        this.applyWishes(map);

        int feelerBrainPos = NO_OF_BRAIN_IN_ARGS;
        for (Feeler feeler : this.feelers) {
            feeler.setBrainOutputVector(outputVector, feelerBrainPos);
            feelerBrainPos += Feeler.NO_OF_BRAIN_IN_ARGS;
            feeler.applyWishes();
        }

    }

    public void becomeOlder() {
        this.age += 1;
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
        brainInputVector[index++] = tile.getFoodLevel();
        brainInputVector[index++] = tile.isWater() ? 1 : 0;
        brainInputVector[index++] = this.hadColision ? 1 : 0;


        for (Feeler feeler : this.feelers) {
            Tile feelerTile = feeler.getCurrentTile(map, this);
            brainInputVector[index++] = feeler.getOcclusion();
            brainInputVector[index++] = feeler.getLength();
            brainInputVector[index++] = feeler.getAngle();
            brainInputVector[index++] = feelerTile.getFoodLevel();
            brainInputVector[index++] = feelerTile.isWater() ? 1 : 0;
        }
        return brainInputVector;
    }

    private void setBrainOutputVector(double[] brainOutputVector, int startBrainInputPos) {

        // FIXME implement brainOutputVector
        if (brainOutputVector != null) {
            int index = startBrainInputPos;
            this.speed = brainOutputVector[index++];
            this.angle = this.angle + brainOutputVector[index++];
            this.wantToEat = brainOutputVector[index++];
            this.wantToGiveBirth = brainOutputVector[index++];
            for (Feeler feeler : this.feelers) {
                feeler.setWantToRotate(brainOutputVector[index++]);
            }
            this.speed = this.wantToBeAtSpeed;
        }


        // FIXME play around.
        this.speed = 2f;
        this.angle = this.angle + 0.1f;
        if (this.angle >= 360) {
            this.angle = this.angle - 360;
        }
        this.wantToEat = 0.8;
    }

    private void applyWishes(final Map map) {
        // Movement of Creature
        int xOffset = (int) (Math.sin(this.angle) * this.speed);
        int yOffset = (int) (Math.cos(this.angle) * this.speed);

        int newPosX = this.posX + xOffset;
        int newPosY = this.posY + yOffset;
        this.hadColision = false;

        int overallRadius = this.getOverallRadius();
        if (newPosX - overallRadius < 0) {
            this.hadColision = true;
            newPosX = overallRadius;
        } else if (newPosX + overallRadius > map.getWidth()) {
            this.hadColision = true;
            newPosX = map.getWidth() - overallRadius;
        }

        if (newPosY - overallRadius < 0) {
            this.hadColision = true;
            newPosY = overallRadius;
        } else if (newPosX + overallRadius > map.getLength()) {
            this.hadColision = true;
            newPosY = map.getLength() - overallRadius;
        }
        this.posX = newPosX;
        this.posY = newPosY;

        // Eat
        if (this.wantToEat > 0) {
            Tile tile = map.getTileUnderPos(this.posX, this.posY);
            double ate = tile.reduceFoodLevel(this.wantToEat);
            double ateEnergyLevel = ate * FOOD_TO_ENERGY_FACTOR;
            //System.out.printf("Creature %s: ate %s\n", this.hashCode(), ateEnergyLevel);
            this.energy += ateEnergyLevel;

        }
        double energyPenalty = this.calulateEnergyPanelty();
        double oldEnergy = this.energy;
        this.energy -= energyPenalty;

        //System.out.printf("Creature %s: %s - %s => %s\n", this.hashCode(), oldEnergy, energyPenalty, this.energy);
    }

    public int getOverallRadius() {
        int radius = this.bodyRadius;
        for (Feeler feeler : this.feelers) {
            radius = Math.max(feeler.getLength(), radius);
        }
        return radius;
    }

    private double calulateEnergyPanelty() {
        // FIXME reasonable factors for wantToEat and speed
        // TODO maybe feeler length as soon as feeler length are growable
        double panelty = ENERGY_LOSS_PER_ROUND + WANT_TO_EAT_IMPACT_FACTOR * this.wantToEat + SPEED_IMPACT_FACTOR * this.speed;
        panelty = this.age * AGE_IMPACT_FACTOR * panelty;
        return panelty;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public boolean isDead() {
        boolean isDead = this.energy < 0;
        //if (isDead)
        //    System.out.printf("Creature %s is dead\n", this.hashCode());
        return isDead;
    }
}
