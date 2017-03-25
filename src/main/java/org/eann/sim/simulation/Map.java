package org.eann.sim.simulation;

/**
 * Created by martin on 16.03.17.
 */
public class Map {
    private Tile[][] tiles;
    private int noOfTileWidth;
    private int noOfTileLength;
    private int tileSize;

    public Map(double[][] heights, int tileSize) {
        this.tiles = new Tile[heights.length][heights[0].length];
        this.tileSize = tileSize;

        this.noOfTileWidth = heights.length;
        this.noOfTileLength = heights[0].length;

        for (int i = 0; i < noOfTileWidth; i++) {
            for (int j = 0; j < noOfTileLength; j++) {
                this.addTitle( new Tile(heights[i][j], i, j) );
            }
        }
    }

    public Tile getTileUnderPos(int x, int y) {
        return getTileAt(x / tileSize, y / tileSize);
    }
    public Tile getTileAt(int x, int y) {
        return this.tiles[x][y];
    }

    private void setTitleAt(int x, int y, Tile tile) {
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

    public int getTileWidth() {
        return noOfTileWidth;
    }
    public int getTileLength() {
        return noOfTileLength;
    }
    public int getTileSize() {
        return tileSize;
    }
    public int getWidth() { return noOfTileWidth * tileSize; }
    public int getLength() { return noOfTileLength * tileSize; }

    public void calculateNextStep() {
        for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < this.tiles[i].length; j++) {
                Tile tile = this.tiles[i][j];

                boolean growMoreFood = false;
                if (! tile.isWater()) {
                    if (tile.isAtMinFood()) {
                        int[][] lookAround = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
                        for (int[] offset : lookAround) {
                            try {
                                int x = i + offset[0];
                                int y = j + offset[1];
                                Tile other = this.tiles[x][y];
                                if (other.isWater() || other.isAtMaxFood()) {
                                    growMoreFood = true;
                                }
                            } catch (IndexOutOfBoundsException ex) {
                                // ignore
                            }
                        }
                    } else if (tile.isNotAtMaxFood() && tile.isNotAtMinFood()) {
                        growMoreFood = true;
                    }
                }

                if(growMoreFood) {
                    tile.growFood();
                }

            }
        }
    }
}
