package org.eann.sim.ui;

import org.eann.sim.simulation.World;

import javax.swing.*;
import java.awt.*;

/**
 * Created by martin on 17.03.17.
 */
public class WorldPanel extends JPanel {

    private World world;
    private int zoomLevel = 8;

    public WorldPanel() {

    }
    public WorldPanel(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();

        if (this.world != null) {
            size.width = Math.max(size.width, this.world.getWidth() * zoomLevel);
            size.height = Math.max(size.height, this.world.getLength() * zoomLevel);
        }

        return size;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (this.world != null) {
            Dimension size = getSize();
            int width = this.world.getWidth();
            int length = this.world.getLength();
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < length; y++) {
                    float height = this.world.getTileAt(x, y).getHeight();
                    float brigthness = 0.8f - Math.abs(height) / 200;
                    // System.out.println(x + " " + y + " : höhe " + height + " zu " + brigthness);
                    Color color = height < 0 ? Color.getHSBColor(.70f, 1.00f, brigthness) : Color.getHSBColor(.40f, 1.00f, brigthness);
                    graphics.setColor(color);
                    graphics.fillRect(x * zoomLevel, y * zoomLevel, zoomLevel, zoomLevel);
                }
            }
        }
    }
}
