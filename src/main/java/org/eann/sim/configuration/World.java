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
    private int height;
    public World() {
        this(100, 100);
    }

    public World(int width, int length) {
        this.width = width;
        this.length = length;
        this.height = 100;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public int getHeight() {
        return height;
    }
}
