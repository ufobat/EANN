package org.eann.sim.simulation;

/**
 * Created by martin on 16.03.17.
 */
public class World {
    private Tile[][] tiles;

    public World(double[][] heights) {
        this.tiles = new Tile[heights.length][heights[0].length];
        for (int i = 0; i < heights.length; i++) {
            for (int j = 0; j < heights[i].length; j++) {
                this.addTitle( new Tile(heights[i][j], i, j) );
            }

        }
    }

    public Tile getTileAt(int x, int y) {
        return this.tiles[x][y];
    }

    public void setTitleAt(int x, int y, Tile tile) {
        this.tiles[x][y] = tile;
    }

    public void addTitle(Tile newTile) {
        if (this.getTileAt(newTile.getX(), newTile.getY())  == null) {
            this.setTitleAt(newTile.getX(), newTile.getY(), newTile);
        }
    }

    public Tile[][] getTiles() {
        return this.tiles;
    }

    public int getWidth() {
        return tiles.length;
    }
    public int getLength() {
        return tiles[0].length;
    }
}
