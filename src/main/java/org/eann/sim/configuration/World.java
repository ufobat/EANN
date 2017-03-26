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

    public int getWidth() {
        return this.width;
    }
    public int getLength() {
        return this.length;
    }
    public int getTileSize() {
        return this.tileSize;
    }
}
