package org.eann.sim.simulation;

/**
 * Created by martin on 18.03.17.
 */
public class Creature {
    private int posX;
    private int poxY;
    private int radius;
    private int age;
    private float energy;
    private float angle;
    private float speed;
    private Feeler[] feelers;

    public Creature(int posX, int poxY, int radius, float energy, float angle, float speed, int age, Feeler[] feelers) {
        this.posX = posX;
        this.poxY = poxY;
        this.radius = radius;
        this.energy = energy;
        this.feelers = feelers;
        this.angle = angle;
        this.speed = speed;
    }


    public Creature() {
        Feeler[] feelers = new Feeler[1];
        feelers[0] = new Feeler();
        new Creature(0,0, 5, 100f, 0, 0, 0, feelers);
    }

}
