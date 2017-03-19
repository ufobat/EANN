package org.eann.sim.ui;

import org.eann.sim.simulation.Creature;
import org.eann.sim.simulation.Map;
import org.eann.sim.simulation.Tile;
import org.eann.sim.simulation.World;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by martin on 17.03.17.
 */
public class WorldPanel extends JPanel {

    private World world;
    private double zoomLevel = 1;

    public WorldPanel() {

    }
    public WorldPanel(World map) {
        this.world = map;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public double getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(double zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();

        size.width = Math.max(size.width, 200);
        size.height = Math.max(size.height, 200);

        if(world != null) {
            size.width = Math.max(size.width, (int) (world.getWidth() * zoomLevel));
            size.height = Math.max(size.height, (int) (world.getLength() * zoomLevel));
        }

        return size;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        this.paintMap(graphics);
        this.paintCreatures(graphics);
        this.paintLegend(graphics);
    }

    private void paintCreatures(Graphics graphics) {
        if (this.world != null) {
            ArrayList<Creature> creatures = this.world.getCreatures();
            for (Creature creature : creatures) {
                paintCreature(creature, graphics);
            }
        }
    }

    private void paintCreature(Creature creature, Graphics graphics) {
        int x = creature.getPositionX();
        int y = creature.getPositionY();
        int radius = creature.getRadius();

        int posx = x+ radius;
        int posy = y + radius;
        int width = 2 * radius;
        int height = 2 * radius;

        drawOval(graphics, Color.BLACK, posx, posy, width, height);
    }

    private void paintLegend(Graphics graphics) {
        if (this.world != null) {
            int lengthOffset = this.world.getLength();

            for (int w = 0; w <= 100; w++) {
                Color color = this.heightToColor( ((float) w)/100);
                this.fillRect(graphics, color, lengthOffset + 1, w, 9, 1);
            }
        }
    }

    private void paintMap(Graphics graphics) {
        if (this.world != null) {
            Map map = this.world.getMap();
            int width = map.getTileWidth();
            int length = map.getTileLength();
            int tileSize = map.getTileSize();

            System.out.println(width);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < length; y++) {
                    System.out.println("x: " + x + "  y: " + y);

                    Tile tile = map.getTileAt(x, y);
                    double height = tile.getHeight();

                    Color color = heightToColor(height);
                    this.fillRect(graphics, color, x*tileSize, y*tileSize, tileSize, tileSize);
                }
            }
        }
    }

    private void drawOval(Graphics graphics, Color color, int x, int y, int sizeX, int sizeY) {
        System.out.printf("Drawing Oval %s %s %s %s\n", x, y, sizeX, sizeY);
        graphics.setColor(color);
        graphics.drawOval((int) (x * zoomLevel), (int) (y * zoomLevel), (int) (sizeX * zoomLevel), (int) (sizeY * zoomLevel));
    }

    private void fillRect(Graphics graphics, Color color, int x, int y, int sizeX, int sizeY) {
        graphics.setColor(color);
        graphics.fillRect((int) (x * zoomLevel), (int) (y * zoomLevel), (int) (sizeX * zoomLevel), (int) (sizeY * zoomLevel));
    }

    private Color heightToColor(double height) {
        float brigthness = (float) 1f - (float) (Math.abs(0.5 - height) / 2);
        // System.out.println("höhe " + height + " zu " + brigthness);
        Color color = height < 0.5 ? Color.getHSBColor(.60f, 0.90f, brigthness) : Color.getHSBColor(.40f, 0.90f, brigthness);
        return color;
    }
}
