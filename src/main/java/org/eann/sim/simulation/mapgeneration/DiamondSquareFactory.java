package org.eann.sim.simulation.mapgeneration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by martin on 18.03.17.
 */
public class DiamondSquareFactory extends AbstractHeightArrayFactory {
    private final Random random;
    private final double impactReduction;

    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public DiamondSquareFactory(final double impactReduction) {
        super();
        this.random = new Random();
        this.impactReduction = impactReduction;
        if (this.impactReduction < 1) {
            throw new IllegalArgumentException("randomImpactReduction must be larger than 1");
        }
    }

    private double getRandomDouble() {
        return random.nextDouble() - 0.5;
    }

    public double[][] buildHeightMap(final int width, final int length) {
        final double[][] map = new double[width][length];

        // uninitialized value
        for (int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                map[i][j] = Double.NaN;
            }
        }

        final double randomimpact = 1;
        final int xmin = 0;
        final int ymin = 0;
        final int xmax = width - 1;
        final int ymax = length - 1;

        setValue(map, xmin, ymin, getRandomDouble());
        setValue(map, xmin, ymax, getRandomDouble());
        setValue(map, xmax, ymin, getRandomDouble());
        setValue(map, xmax, ymax, getRandomDouble());

        calcSquareDiamond(map, xmin, xmax, ymin, ymax, randomimpact);
        normalize(map);
        // printGrid(map);
        return map;
    }

    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    private void calcSquareDiamond(final double[][] map, final int xmin, final int xmax, final int ymin, final int ymax, final double randomimpact) {
        double newHeight = 0;
        final int newX = (xmin + xmax) / 2;
        final int newY = (ymin + ymax) / 2;

        // is already propper initialized
        if (Double.isNaN( map[newX][newY] )) {

            final int[][] coords = {
                    {xmin, ymin},
                    {xmax, ymin},
                    {xmax, ymax},
                    {xmin, ymax}
            };

            // Diamond
            for (final int[] pos : coords) {
                final int x = pos[0];
                final int y = pos[1];
                newHeight += map[x][y];
            }
            setValue(map, newX, newY, newHeight / 4 + getRandomDouble() * randomimpact);

            // Square
            final ArrayList<Cell> history = new ArrayList<>();
            for (final int[] pos : coords) {
                final int x = pos[0];
                final int y = pos[1];
                history.add(0, new Cell(x, y, map[x][y]));

                if (history.size() >= 2) {
                    makeHeight(map, history, newHeight, randomimpact);
                }
            }
            history.add(0, history.get(history.size() - 1));
            makeHeight(map, history, newHeight, randomimpact);

            final double nri = randomimpact / impactReduction;

            // bottom left
            this.calcSquareDiamond(map, xmin, newX, ymin, newY, nri);

            // bottom right
            this.calcSquareDiamond(map, newX, xmax, ymin, newY, nri);

            // top left
            this.calcSquareDiamond(map, xmin, newX, newY, ymax, nri);

            // top right
            this.calcSquareDiamond(map, newX, xmax, newY, ymax, nri);
        }
    }

    private void makeHeight(final double[][] map, final List<Cell> history, final double lastHeight, final double randomimpact) {
        final double newHeight = ( history.get(0).height + history.get(1).height + lastHeight ) / 3;
        final int addx = (history.get(0).x + history.get(1).x) / 2;
        final int addy = (history.get(0).y + history.get(1).y) / 2;
        setValue(map, addx, addy, newHeight + getRandomDouble() * randomimpact);
    }
    private void setValue(final double[][] map, final int x, final int y, final double val) {
        // System.out.println("Setting " + x + ", " + y + " to " + val);
        if (Double.isNaN(map[x][y])) {
            map[x][y] = val;
        }
    }

    private class Cell {
        public final int x;
        public final int y;
        public final double height;
        public Cell(final int x, final int y, final double height) {
            this.x = x;
            this.y = y;
            this.height = height;
        }
    }
}
