package org.eann.sim.simulation;

/**
 * Created by martin on 18.03.17.
 */
public class Creature {
    private int posX;
    private int posY;
    private int radius;
    private int age;
    private float energy;
    private float angle;
    private float speed;
    private Feeler[] feelers;

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
}
