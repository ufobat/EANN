package org.eann.sim.ui;

import org.eann.sim.simulation.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Vector;

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
        Graphics2D g2d = (Graphics2D) graphics;
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.scale(zoomLevel, zoomLevel);
        g2d.transform(affineTransform);
        this.paintMap(g2d);
        this.paintCreatures(g2d);
    }

    private void paintCreatures(Graphics2D graphics) {
        if (this.world != null) {

            // FIXME arrayList ist not multithreadsave
            ArrayList<Creature> creatures = this.world.getCreatures();
            for (Creature creature : creatures) {
                this.paintCreature(creature, graphics);
            }
        }
    }

    private void paintCreature(Creature creature, Graphics2D graphics) {
        int positionX = creature.getPosX();
        int positionY = creature.getPosY();
        int radius = creature.getBodyRadius();
        int creatureStartX = positionX - radius;
        int creatureStartY = positionY - radius;
        int creatureWidth = radius + radius;
        int creatureHeight = radius + radius;
        drawOval(graphics, Color.BLACK, creatureStartX, creatureStartY, creatureWidth, creatureHeight);

        for(Feeler feeler: creature.getFeelers()) {
            int feelerStartX = positionX;
            int feelerStartY = positionY;
            int feelerEndX = feeler.getSensorPosX(feelerStartX);
            int feelerEndY = feeler.getSensorPosY(feelerStartY);
            drawLine(graphics, Color.BLACK, feelerStartX, feelerStartY, feelerEndX, feelerEndY);
        }
    }

    private void paintMap(Graphics2D graphics) {
        if (this.world != null) {
            Map map = this.world.getMap();
            int width = map.getTileWidth();
            int length = map.getTileLength();
            int tileSize = map.getTileSize();

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < length; y++) {
                    Tile tile = map.getTileAt(x, y);
                    Color color = this.tileToColor(tile);
                    this.fillRect(graphics, color, x*tileSize, y*tileSize, tileSize, tileSize);
                }
            }
        }
    }

    private void drawLine(Graphics2D graphics, Color color, int x1, int x2, int y1, int y2) {
        graphics.setColor(color);
        graphics.drawLine(x1, x2, y1, y2);
    }

    private void drawOval(Graphics2D graphics, Color color, int x, int y, int sizeX, int sizeY) {
        graphics.setColor(color);
        graphics.drawOval(x, y, sizeX, sizeY);
    }

    private void fillRect(Graphics2D graphics, Color color, int x, int y, int sizeX, int sizeY) {
        graphics.setColor(color);
        graphics.fillRect(x, y, sizeX, sizeY);
    }

    private Color tileToColor(Tile tile) {
        double height = tile.getHeight();
        float brigthness = (float) 1f - (float) (Math.abs(0.5 - height) / 2);
        Color color;
        if (height < 0.5) {
            // Water
            color = Color.getHSBColor(.60f, 0.90f, brigthness);
        } else {
            // Land
            float hue = .20f + (float) (0.15f * tile.getFoodLevelNormalized());
            color = Color.getHSBColor(hue, 0.90f, brigthness);
        }
        return color;
    }
}
