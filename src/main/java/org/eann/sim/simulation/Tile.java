package org.eann.sim.simulation;

public class Tile {
    private double hight;
    private int x;
    private int y;

    public Tile(double hight, int x, int y) {
        this.hight = hight;
        this.x = x;
        this.y = y;
    }

    public double getHeight() {
        return this.hight;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
