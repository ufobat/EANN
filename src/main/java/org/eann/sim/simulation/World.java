package org.eann.sim.simulation;


/**
 * Created by martin on 16.03.17.
 */
public class World {
    private Tile[][] tile;

    public World(int width, int length) {
        this.tile = new Tile[width][length];
    }

    public Tile getTileAt(int x, int y) {
        return this.tile[x][y];
    }

    public void setTitleAt(int x, int y, Tile tile) {
        this.tile[x][y] = tile;
    }

    public void addTitle(Tile newTile) {
        this.setTitleAt(newTile.getX(), newTile.getY(), newTile);
    }
}
