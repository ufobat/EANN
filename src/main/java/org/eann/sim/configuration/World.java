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
        this(300, 300, 100);
    }

    public World(int width, int length, int height) {
        this.width = width;
        this.length = length;
        this.height = height;
    }
}
