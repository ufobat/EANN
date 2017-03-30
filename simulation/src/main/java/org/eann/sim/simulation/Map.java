package org.eann.sim.simulation;

/**
 * Created by martin on 16.03.17.
 */
public class Map {
    private Tile[][] tiles;
    private final int noOfTileWidth;
    private final int noOfTileLength;
    private final int tileSize;

    @SuppressWarnings({"PMD.AvoidInstantiatingObjectsInLoops", "PMD.AvoidDuplicateLiterals", "PMD.ConstructorOnlyInitializesOrCallOtherConstructors", "PMD.ArrayIsStoredDirectly"})
    public Map(final double[][] heights, final int tileSize) {
        this.tiles = new Tile[heights.length][heights[0].length];
        this.tileSize = tileSize;

        this.noOfTileWidth = heights.length;
        this.noOfTileLength = heights[0].length;

        for (int i = 0; i < this.noOfTileWidth; i++) {
            for (int j = 0; j < this.noOfTileLength; j++) {
                this.addTitle( new Tile(heights[i][j], i, j) );
            }
        }
    }

    @SuppressWarnings({"PMD.AvoidInstantiatingObjectsInLoops", "PMD.AvoidDuplicateLiterals"})
    public void calculateNextStep() {
        for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < this.tiles[i].length; j++) {
                final Tile tile = this.tiles[i][j];

                boolean growMoreFood = false;
                if (! tile.isWater()) {
                    if (tile.isAtMinFood()) {
                        growMoreFood = this.isNeighborFertile(i, j);
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

    @SuppressWarnings("PMD.EmptyCatchBlock")
    private boolean isNeighborFertile(final int i, final int j) {
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

    public void addTitle(final Tile newTile) {
        if (this.getTileAt(newTile.getX(), newTile.getY())  == null) {
            this.setTitleAt(newTile.getX(), newTile.getY(), newTile);
        }
    }

    public Tile getTileUnderPos(final int x, final int y) {
        return getTileAt(x / tileSize, y / tileSize);
    }
    public Tile getTileAt(final int x, final int y) {
        return this.tiles[x][y];
    }
    private void setTitleAt(final int x, final int y, final Tile tile) {
        this.tiles[x][y] = tile;
    }

    @SuppressWarnings("PMD.MethodReturnsInternalArray")
    public Tile[][] getTiles() {
        return this.tiles;
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
