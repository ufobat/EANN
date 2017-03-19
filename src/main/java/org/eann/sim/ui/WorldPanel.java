package org.eann.sim.ui;

import org.eann.sim.simulation.Tile;
import org.eann.sim.simulation.Map;

import javax.swing.*;
import java.awt.*;

/**
 * Created by martin on 17.03.17.
 */
public class WorldPanel extends JPanel {

    private Map map;
    private double zoomLevel = 1;

    public WorldPanel() {

    }
    public WorldPanel(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
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

        if(map != null) {
            size.width = Math.max(size.width, (int) (map.getWidth() * zoomLevel));
            size.height = Math.max(size.height, (int) (map.getLength() * zoomLevel));
        }

        return size;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        this.paintWorld(graphics);
        this.paintLegend(graphics);
    }

    private void paintLegend(Graphics graphics) {
        if (this.map != null) {
            int lengthOffset = this.map.getLength();

            for (int w = 0; w <= 100; w++) {
                Color color = this.heightToColor( ((float) w)/100);
                this.fillRect(graphics, color, lengthOffset + 1, w, 9, 1);
            }
        }
    }

    private void paintWorld(Graphics graphics) {
        if (this.map != null) {
            int width = this.map.getTileWidth();
            int length = this.map.getTileLength();
            int tileSize = this.map.getTileSize();

            System.out.println(width);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < length; y++) {
                    System.out.println("x: " + x + "  y: " + y);

                    Tile tile = this.map.getTileAt(x, y);
                    double height = tile.getHeight();

                    Color color = heightToColor(height);
                    this.fillRect(graphics, color, x*tileSize, y*tileSize, tileSize, tileSize);
                }
            }
        }
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
