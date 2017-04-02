package org.eann.sim.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by martin on 17.03.17.
 */
@XmlAccessorType(value= XmlAccessType.FIELD)
public class World {
    private int width;
    private int length;
    private int tileSize;

    public World() {
        this(80, 80, 25);
    }

    public World(final int width, final int length, final int tileSize) {
        this.width = width;
        this.length = length;
        this.tileSize = tileSize;
    }

    public World(final World world) {
        this(world.width, world.length, world.tileSize);
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
