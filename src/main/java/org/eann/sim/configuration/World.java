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
        this(100, 100, 10);
    }

    public World(int width, int length, int tileSize) {
        this.width = width;
        this.length = length;
        this.tileSize = tileSize;
    }

    public int getWidth() {
        return width;
    }
    public int getLength() {
        return length;
    }
    public int getTileSize() {
        return tileSize;
    }
}
