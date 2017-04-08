package org.eann.sim.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by martin on 17.03.17.
 */
@XmlAccessorType(value= XmlAccessType.FIELD)
public class WorldSettings {
    private int width;
    private int length;
    private int tileSize;

    public WorldSettings() {
        this(80, 60, 25);
    }

    public WorldSettings(final int width, final int length, final int tileSize) {
        this.width = width;
        this.length = length;
        this.tileSize = tileSize;
    }

    public WorldSettings(final WorldSettings worldSettings) {
        this(worldSettings.width, worldSettings.length, worldSettings.tileSize);
    }

    public void applyConfiguration(final WorldSettings worldSettings) {
        this.setLength(worldSettings.getLength());
        this.setTileSize(worldSettings.getTileSize());
        this.setWidth(worldSettings.getWidth());
    }

    public int getWidth() {
        return this.width;
    }
    public int getLength() {
        return this.length;
    }
    public int getTileSize() {
        return this.tileSize;
    }
    public void setLength(final int length) {
        this.length = length;
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public void setTileSize(final int tileSize) {
        this.tileSize = tileSize;
    }
}
