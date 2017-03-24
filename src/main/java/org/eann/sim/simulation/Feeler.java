package org.eann.sim.simulation;

/**
 * Created by martin on 19.03.17.
 */
public class Feeler {
    public static int NO_OF_BRAIN_IN_ARGS = 5;

    private int length;
    private double angle;
    private double occlusion;
    private double setWantToRotate;

    public Feeler(int length, float angle) {
        this.length = length;
        this.angle = angle;
    }

    public int getLength() {
        return length;
    }

    public double getAngle() {
        return angle;
    }

    public double getOcclusion() {
        return this.occlusion;
    }

    public void setWantToRotate(double wantToRotate) {
        this.setWantToRotate = wantToRotate;
    }

    public void applyWishes() {
    }

    public Tile getCurrentTile(Map map, Creature creature) {
        return map.getTileUnderPos(this.getSensorPosX(creature.getPosX()), this.getSensorPosY(creature.getPosY()));
    }

    public int getSensorPosX(int creatureX) {
        int feelerEndX = (int) (Math.sin(this.angle) * this.length) + creatureX;
        return feelerEndX;
    }
    public int getSensorPosY(int creatureY) {
        int feelerEndY = (int) (Math.cos(this.angle) * this.length) + creatureY;
        return feelerEndY;
    }

    public void setBrainOutputVector(double[] brainOutputVector, int startBrainInputPos) {

    }
}
