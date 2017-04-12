package org.eann.sim.simulation;

import java.io.Serializable;

/**
 * Created by martin on 16.03.17.
 */
public class Map implements Serializable {
    private static final long serialVersionUID = -5168785911985141314L;
    private final Tile[][] tiles;
    private final int noOfTileWidth;
    private final int noOfTileLength;
    private final int tileSize;

    @SuppressWarnings({"PMD.AvoidInstantiatingObjectsInLoops", "PMD.AvoidDuplicateLiterals", "PMD.ConstructorOnlyInitializesOrCallOtherConstructors", "PMD.ArrayIsStoredDirectly"})
    public Map(final Tile[][] tiles, final int tileSize) {
        this.tiles = tiles;
        this.tileSize = tileSize;
        this.noOfTileWidth = tiles.length;
        this.noOfTileLength = tiles[0].length;
    }

    public Map(final Map map) {
        this(map.getClonedTiles(), map.tileSize);
    }

    @SuppressWarnings("PMD.EmptyCatchBlock")
    public boolean isNeighborFertile(final int i, final int j) {
        boolean isFertile = false;
        final int[][] lookAround = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        for (final int[] offset : lookAround) {
            try {
                final int x = i + offset[0];
                final int y = j + offset[1];
                final Tile other = this.tiles[x][y];
                if (other.isWater() || other.isAtMaxFood()) {
                    isFertile = true;
                }
            } catch (IndexOutOfBoundsException ex) {
                // ignore if there is no neighbor tile
            }
        }
        return isFertile;
    }


    public Tile getTileUnderPos(final int x, final int y) {
        return getTileAt(x / tileSize, y / tileSize);
    }
    public Tile getTileAt(final int x, final int y) {
        return this.tiles[x][y];
    }

    @SuppressWarnings("PMD.MethodReturnsInternalArray")
    public Tile[][] getTiles() {
        return this.tiles;
    }

    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public Tile[][] getClonedTiles() {
        Tile[][] tiles = new Tile[this.tiles.length][this.tiles[0].length];
        for (int i = 0; i <this.tiles.length; i++) {
            for (int j = 0; j < this.tiles[i].length; j++) {
                tiles[i][j] = new Tile(this.tiles[i][j]);
            }
        }
        return tiles;
    }
    public int getTileWidth() {
        return this.noOfTileWidth;
    }
    public int getTileLength() {
        return this.noOfTileLength;
    }
    public int getTileSize() {
        return this.tileSize;
    }
    public int getWidth() { return this.noOfTileWidth * this.tileSize; }
    public int getLength() { return this.noOfTileLength * this.tileSize; }
}
