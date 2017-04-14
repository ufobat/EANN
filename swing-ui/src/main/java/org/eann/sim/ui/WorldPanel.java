package org.eann.sim.ui;

import org.eann.sim.simulation.*;
import org.eann.sim.simulation.creature.CreatureState;
import org.eann.sim.simulation.creature.FamilyRegister;
import org.eann.sim.simulation.creature.FeelerState;
import org.eann.sim.simulation.dataexchange.CreatureBean;
import org.eann.sim.simulation.dataexchange.Snapshot;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Set;
import java.util.UUID;

/**
 * Created by martin on 17.03.17.
 */
public class WorldPanel extends JPanel {

    private static final long serialVersionUID = 3967893309093169658L;
    private World world;
    private final ColorManager colorManager;
    private double zoomLevel;

    public WorldPanel() {
        super();
        this.colorManager = new ColorManager();
        this.zoomLevel = 1;
    }

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
        if (this.world != null) {
            final Snapshot snapshot = this.world.getSnapshot();
            this.paintMap(g2d, snapshot);
            this.paintCreatures(g2d, snapshot);
        }
    }

    private void paintCreatures(final Graphics2D graphics, final Snapshot snapshot) {
        if (this.world != null) {
            final Set<CreatureBean> creatureBeans = snapshot.getCreatures();

            this.colorManager.startTransaction();
            for (final CreatureBean bean : creatureBeans) {
                final FamilyRegister register = bean.getRegister();
                final UUID uuid = register.getTribe();
                final Color color = this.colorManager.colorForUUID(uuid);
                this.paintCreature(bean.getState(), color, graphics);
            }
            this.colorManager.endTransaction();
        }
    }

    private void paintCreature(final CreatureState creatureState, final Color color, final Graphics2D graphics) {
        final int positionX = creatureState.getPosX();
        final int positionY = creatureState.getPosY();
        final int radius = creatureState.getBodyRadius();
        final int creatureStartX = positionX - radius;
        final int creatureStartY = positionY - radius;
        final int creatureWidth = radius + radius;
        final int creatureHeight = radius + radius;
        fillOval(graphics, color, creatureStartX, creatureStartY, creatureWidth, creatureHeight);
        drawOval(graphics, Color.BLACK, creatureStartX, creatureStartY, creatureWidth, creatureHeight);

        for(final FeelerState feelerState : creatureState.getFeelerStates()) {
            final int feelerStartX = positionX;
            final int feelerStartY = positionY;
            final int feelerEndX = feelerState.getSensorPosX(feelerStartX);
            final int feelerEndY = feelerState.getSensorPosY(feelerStartY);
            drawLine(graphics, Color.BLACK, feelerStartX, feelerStartY, feelerEndX, feelerEndY);
        }
    }

    private void paintMap(final Graphics2D graphics, final Snapshot snapshot) {
        if (this.world != null) {
            final Map map = snapshot.getMap();
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

    private void fillOval(final Graphics2D graphics, final Color color, final int x, final int y, final int sizeX, final int sizeY) {
        graphics.setColor(color);
        graphics.fillOval(x, y, sizeX, sizeY);
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
            final float hue = .20f + (float) (0.15f * tile.getFoodLevel());
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
