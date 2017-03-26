package org.eann.sim.ui;

import org.eann.sim.simulation.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Set;

/**
 * Created by martin on 17.03.17.
 */
public class WorldPanel extends JPanel {

    private World world;
    private double zoomLevel = 1;

    @Override
    public Dimension getPreferredSize() {
        final Dimension size = super.getPreferredSize();

        size.width = Math.max(size.width, 200);
        size.height = Math.max(size.height, 200);

        if(world != null) {
            size.width = Math.max(size.width, (int) (world.getWidth() * zoomLevel));
            size.height = Math.max(size.height, (int) (world.getLength() * zoomLevel));
        }

        return size;
    }

    @Override
    protected void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);
        final Graphics2D g2d = (Graphics2D) graphics;
        final AffineTransform affineTransform = new AffineTransform();
        affineTransform.scale(zoomLevel, zoomLevel);
        g2d.transform(affineTransform);
        this.paintMap(g2d);
        this.paintCreatures(g2d);
    }

    private void paintCreatures(final Graphics2D graphics) {
        if (this.world != null) {


            final Set<Creature> creatures = this.world.getCreatures();
            for (final Creature creature : creatures) {
                this.paintCreature(creature, graphics);
            }
        }
    }

    private void paintCreature(final Creature creature, final Graphics2D graphics) {
        final int positionX = creature.getPosX();
        final int positionY = creature.getPosY();
        final int radius = creature.getBodyRadius();
        final int creatureStartX = positionX - radius;
        final int creatureStartY = positionY - radius;
        final int creatureWidth = radius + radius;
        final int creatureHeight = radius + radius;
        drawOval(graphics, Color.BLACK, creatureStartX, creatureStartY, creatureWidth, creatureHeight);

        for(final Feeler feeler: creature.getFeelers()) {
            final int feelerStartX = positionX;
            final int feelerStartY = positionY;
            final int feelerEndX = feeler.getSensorPosX(feelerStartX);
            final int feelerEndY = feeler.getSensorPosY(feelerStartY);
            drawLine(graphics, Color.BLACK, feelerStartX, feelerStartY, feelerEndX, feelerEndY);
        }
    }

    private void paintMap(final Graphics2D graphics) {
        if (this.world != null) {
            final Map map = this.world.getMap();
            final int width = map.getTileWidth();
            final int length = map.getTileLength();
            final int tileSize = map.getTileSize();

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < length; y++) {
                    final Tile tile = map.getTileAt(x, y);
                    final Color color = this.tileToColor(tile);
                    this.fillRect(graphics, color, x*tileSize, y*tileSize, tileSize, tileSize);
                }
            }
        }
    }

    private void drawLine(final Graphics2D graphics, final Color color, final int x1, final int x2, final int y1, final int y2) {
        graphics.setColor(color);
        graphics.drawLine(x1, x2, y1, y2);
    }

    private void drawOval(final Graphics2D graphics, final Color color, final int x, final int y, final int sizeX, final int sizeY) {
        graphics.setColor(color);
        graphics.drawOval(x, y, sizeX, sizeY);
    }

    private void fillRect(final Graphics2D graphics, final Color color, final int x, final int y, final int sizeX, final int sizeY) {
        graphics.setColor(color);
        graphics.fillRect(x, y, sizeX, sizeY);
    }

    private Color tileToColor(final Tile tile) {
        final double height = tile.getHeight();
        final float brightness = 1f - (float) (Math.abs(0.5 - height) / 2);
        Color color;
        if (height < 0.5) {
            // Water
            color = Color.getHSBColor(.60f, 0.90f, brightness);
        } else {
            // Land
            final float hue = .20f + (float) (0.15f * tile.getFoodLevelNormalized());
            color = Color.getHSBColor(hue, 0.90f, brightness);
        }
        return color;
    }

    public void setWorld(final World world) {
        this.world = world;
    }

    public double getZoomLevel() {
        return this.zoomLevel;
    }

    public void setZoomLevel(final double zoomLevel) {
        this.zoomLevel = zoomLevel;
    }
}
