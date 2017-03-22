package org.eann.sim.simulation;

/**
 * Created by martin on 18.03.17.
 */
public class Creature {
    private static final int NO_OF_BRAIN_IN_ARGS = 3;

    // information about me
    private int posX;
    private int posY;
    private int radius;
    private int age;
    private float energy;
    private float angle;
    private float speed;
    private Feeler[] feelers;
    private boolean hadColision;

    // things my brain wants me to do
    private float wantToEat;
    private float wantToAccelerate;
    private float wantToRotate;
    private float wantToGiveBirth;

    public Creature(final int posX, final int posY, final int radius, final float energy, final float angle, final float speed, final int age, final Feeler[] feelers) {
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
        this(posX, posY, 5, 100, 0, 0,0, new Feeler[] { new Feeler(5, 0)});
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
        float[] inputVector = this.getBrainInputVector(map);
        // feed input vector to bain
        // get output vector from brain
        float[] outputVector = null;
        this.setBrainOutputVector(outputVector);
        // adjust self according to output Vector

        this.applyWishes();
        for (Feeler feeler : this.feelers) {
            feeler.applyWishes();
        }

        this.realizeWishes(map);
    }

    private void realizeWishes(final Map map) {
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
        System.out.println("Moving Creature to x " + this.posX + ","  + this.posY);
    }

    private void applyWishes() {
        this.angle = this.angle + this.wantToRotate;
        this.speed = this.speed + this.wantToAccelerate;

        // FIXME want to eat?
    }

    private void setBrainOutputVector(float[] brainOutputVector) {
        int index = 0;
        this.wantToAccelerate = brainOutputVector[index++];
        this.wantToRotate = brainOutputVector[index++];
        this.wantToEat = brainOutputVector[index++];
        this.wantToGiveBirth = brainOutputVector[index++];
        for(Feeler feeler: this.feelers) {
            feeler.setWantToRotate(brainOutputVector[index++]);
        }

    }

    private float[] getBrainInputVector(final Map map) {
        int noOfBrainInputElements = Creature.NO_OF_BRAIN_IN_ARGS + this.feelers.length * Feeler.NO_OF_BRAIN_IN_ARGS;
        float[] brainInputVector = new float[noOfBrainInputElements];
        int index = 0;

        brainInputVector[index++] = 1f; // bias neuron
        brainInputVector[index++] = this.energy;
        brainInputVector[index++] = this.age;
        brainInputVector[index++] = 0; // food value on creature
        brainInputVector[index++] = 0; // water on creature


        for(Feeler feeler: this.feelers) {
            brainInputVector[index++] = feeler.getOcclusion();
            brainInputVector[index++] = feeler.getLength();
            brainInputVector[index++] = 0; // food value on feeler
            brainInputVector[index++] = 0; // water on feeler
        }
        return brainInputVector;
    }
}
