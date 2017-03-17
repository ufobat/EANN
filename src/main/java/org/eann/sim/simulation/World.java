package org.eann.sim.simulation;

/**
 * Created by martin on 16.03.17.
 */
public class World {
    private Tile[][] tiles;

    public World(int width, int length) {
        this.tiles = new Tile[width][length];
    }

    public Tile getTileAt(int x, int y) {
        return this.tiles[x][y];
    }

    public void setTitleAt(int x, int y, Tile tile) {
        this.tiles[x][y] = tile;
    }

    public void addTitle(Tile newTile) {
        this.setTitleAt(newTile.getX(), newTile.getY(), newTile);
    }

    public Tile[][] getTiles() {
        return this.tiles;
    }
}
