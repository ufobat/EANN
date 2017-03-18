package org.eann.sim.simulation;

/**
 * Created by martin on 16.03.17.
 */
public class World {
    private Tile[][] tiles;
    private int width;
    private int length;
    private int tileSize;

    public World(double[][] heights, int tileSize) {
        this.tiles = new Tile[heights.length][heights[0].length];
        this.tileSize = tileSize;
        int width = heights.length;
        int length = heights[0].length;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                this.addTitle( new Tile(heights[i][j], i, j) );
            }
        }

        this.width = width * this.tileSize;
        this.length = length * this.tileSize;
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
