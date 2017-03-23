package org.eann.sim.simulation;

/**
 * Created by martin on 18.03.17.
 */
public class Creature {
    private static final int NO_OF_BRAIN_IN_ARGS = 6;

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    // information about me
    private int posX;
    private int posY;
    private int radius;
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

    public Creature(final int posX, final int posY, final int radius, final double energy, final double angle, final double speed, final int age, final Feeler[] feelers) {
        this.posX = posX;
        this.posY = posY;
        this.radius = radius;
        this.energy = energy;
        this.feelers = feelers;
        this.angle = angle;
        this.speed = speed;
        this.age = age;
    }

    public Creature(int posX, int posY) {
        this(posX, posY, 5, 100, 0, 0,0, new Feeler[] { new Feeler(10, 0)});
    }

    public Creature() {
        this(0,0);
    }

    public int getPositionX() {
        return posX;
    }

    public int getPositionY() {
        return posY;
    }

    public int getRadius() {
        return radius;
    }

    public Feeler[] getFeelers() {
        return feelers;
    }

    public void calculateNextStep(final Map map) {
        // build input vector
        double[] inputVector = this.getBrainInputVector(map);
        // feed input vector to bain
        // get output vector from brain
        double[] outputVector = null;
        this.setBrainOutputVector(outputVector, 0);
        this.applyWishes(map);
        // adjust self according to output Vector

        int feelerBrainPos = NO_OF_BRAIN_IN_ARGS;
        for (Feeler feeler : this.feelers) {
            feeler.setBrainOutputVector(outputVector, feelerBrainPos);
            feelerBrainPos += Feeler.NO_OF_BRAIN_IN_ARGS;
            feeler.applyWishes();
        }

    }

    private void applyWishes(final Map map) {
        int xOffset = (int) (Math.sin(this.angle) * this.speed);
        int yOffset = (int) (Math.cos(this.angle) * this.speed);

        int newPosX = this.posX + xOffset;
        int newPosY = this.posY + yOffset;
        this.hadColision = false;

        if (newPosX - this.radius < 0) {
            this.hadColision = true;
            newPosX = this.radius;
        } else if (newPosX + this.radius > map.getWidth()) {
            this.hadColision = true;
            newPosX = map.getWidth() - this.radius;
        }

        if (newPosY - this.radius < 0) {
            this.hadColision = true;
            newPosY = this.radius;
        } else if (newPosX + this.radius > map.getLength()) {
            this.hadColision = true;
            newPosY = map.getLength() - this.radius;
        }
        this.posX = newPosX;
        this.posY = newPosY;

        // FIXME want to eat?
        double energyPenalty = this.calulateEnergyPanelty();

        // System.out.println("Moving Creature to x " + this.posX + ","  + this.posY);
    }

    private double calulateEnergyPanelty() {
        // FIXME reasnonable factors for wantToEat and speed
        // TODO maybe feeler length as soon as feeler length are growable
        double panelty = this.age * ( this.wantToEat + this.speed);
        return panelty;
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

    }

    private double[] getBrainInputVector(final Map map) {
        int noOfBrainInputElements = Creature.NO_OF_BRAIN_IN_ARGS + this.feelers.length * Feeler.NO_OF_BRAIN_IN_ARGS;
        double[] brainInputVector = new double[noOfBrainInputElements];
        int index = 0;
        Tile tile = map.getTileAt(this.posX, this.posY);
        brainInputVector[index++] = 1; // bias neuron
        brainInputVector[index++] = this.energy;
        brainInputVector[index++] = this.age;
        brainInputVector[index++] = tile.getFoodLevel();
        brainInputVector[index++] = tile.isWater() ? 1 : 0;
        brainInputVector[index++] = this.hadColision ? 1 : 0;


        for(Feeler feeler: this.feelers) {
            Tile feelerTile = feeler.getCurrentTile(map, this);
            brainInputVector[index++] = feeler.getOcclusion();
            brainInputVector[index++] = feeler.getLength();
            brainInputVector[index++] = feeler.getAngle();
            brainInputVector[index++] = feelerTile.getFoodLevel();
            brainInputVector[index++] = feelerTile.isWater() ? 1 : 0;
        }
        return brainInputVector;
    }
}
