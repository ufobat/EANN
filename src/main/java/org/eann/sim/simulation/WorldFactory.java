package org.eann.sim.simulation;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by martin on 17.03.17.
 */
public class WorldFactory {
    private final Random randomGenerator;
    private final org.eann.sim.configuration.World worldConfiguration;
    private final float randomNumberImpactDevisor = 1.4f;

    public WorldFactory(org.eann.sim.configuration.World worldConfiguration) {
        this.randomGenerator = new Random();
        this.worldConfiguration = worldConfiguration;
    }
    public World buildWorld() {
        World world = new World(this.worldConfiguration.getWidth(), this.worldConfiguration.getLength());

        int height  = this.worldConfiguration.getHeight();
        int xmin = 0;
        int ymin = 0;
        int xmax = this.worldConfiguration.getWidth() - 1;
        int ymax = this.worldConfiguration.getLength() - 1;

        float randomimpact = 0.5f;
        world.addTitle(this.buildTile(xmin, ymin, randomimpact));
        world.addTitle(this.buildTile(xmin, ymax, randomimpact));
        world.addTitle(this.buildTile(xmax, ymin, randomimpact));
        world.addTitle(this.buildTile(xmax, ymax, randomimpact));

        this.calcSquareDiamond(world, xmin, xmax, ymin, ymax, randomimpact);

        return world;
    }

    private void calcSquareDiamond(World world, int xmin, int xmax, int ymin, int ymax, float randomimpact) {
        float newHeight = this.getRandomHeight() * randomimpact;

        int newX = (xmin + xmax) / 2;
        int newY = (ymin + ymax) / 2;

        if (world.getTileAt(newX, newY) != null) {
            return;
        }

        int[][] coords = {
                {xmin, ymin},
                {xmax, ymin},
                {xmax, ymax},
                {xmin, ymax}
        };

        // Diamond
        for(int[] pos: coords) {
            int x = pos[0];
            int y = pos[1];
            newHeight += world.getTileAt(x, y).getHeight();
        }
        Tile middleTile = this.buildTile(newHeight / 5, newX, newY);
        world.addTitle(middleTile);

        // Square
        ArrayList<Tile> history = new ArrayList<Tile>();
        for(int[] pos: coords) {
            int x = pos[0];
            int y = pos[1];
            history.add(0, world.getTileAt(x, y));
            if ( history.size() >= 2 ) {
                world.addTitle(makeATile(history, middleTile, randomimpact));
            }
        }
        history.add(0, history.get(history.size() - 1 ));
        world.addTitle(makeATile(history, middleTile, randomimpact));

        // bottom left
        float newRi = randomimpact / randomNumberImpactDevisor;
        this.calcSquareDiamond(world, xmin, newX, ymin, newY, newRi);

        // bottom right
        this.calcSquareDiamond(world, newX, xmax, ymin, newY, newRi);

        // top left
        this.calcSquareDiamond(world, xmin, newX, newY, ymax, newRi);

        // top right
        this.calcSquareDiamond(world, newX, xmax,newY, ymax, newRi);
    }

    private Tile makeATile(ArrayList<Tile> history, Tile middleTile, float randomimpact) {
        float newHeight = this.getRandomHeight()*randomimpact + history.get(0).getHeight() + history.get(1).getHeight() + middleTile.getHeight();
        int addx = (history.get(0).getX() + history.get(1).getX()) / 2;
        int addy = (history.get(0).getY() + history.get(1).getY()) / 2;
        Tile tile = this.buildTile(newHeight / 4, addx, addy);
        return tile;
    }


    private float getRandomHeight() {
        int height = this.worldConfiguration.getHeight();
        return this.randomGenerator.nextFloat()*2*height - height;
    }

    private Tile buildTile(float height, int x, int y) {
        return new Tile(height, x, y);
    }
    private Tile buildTile(int x, int y, float randomimpact) {
        return this.buildTile(this.getRandomHeight() * randomimpact, x, y);
    }
}
