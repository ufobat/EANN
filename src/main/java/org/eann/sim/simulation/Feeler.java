package org.eann.sim.simulation;

/**
 * Created by martin on 19.03.17.
 */
public class Feeler {
    public final static int BRAIN_IN_ARGS = 5;
    public final static int BRAIN_OUT_ARGS = 1;

    private final int length;
    private double angle;
    private double wantToRotate;
    // FIXME this needs to be implemented
    private final double occlusion;

    public Feeler(final int length, final float angle) {
        this.length = length;
        this.angle = angle;
        this.occlusion = 0;
    }

    @SuppressWarnings("PMD.ArrayIsStoredDirectly")
    public void setBrainOutputVector(final double[] brainOutputVector, final int startPos) {
        this.wantToRotate = brainOutputVector[startPos];
    }

    public void applyWishes() {
        final double modulo = (this.angle + this.wantToRotate) % (2 * Math.PI);
        this.angle = modulo < 0 ? modulo + 2 * Math.PI : modulo;
    }

    public Tile getCurrentTile(final Map map, final Creature creature) {
        return map.getTileUnderPos(this.getSensorPosX(creature.getPosX()), this.getSensorPosY(creature.getPosY()));
    }

    public int getSensorPosX(final int creatureX) {
        return (int) (Math.sin(this.angle) * this.length) + creatureX;
    }
    public int getSensorPosY(final int creatureY) {
        return (int) (Math.cos(this.angle) * this.length) + creatureY;
    }
    public int getLength() {
        return this.length;
    }
    public double getAngle() {
        return this.angle;
    }
    public double getOcclusion() {
        return this.occlusion;
    }
}
