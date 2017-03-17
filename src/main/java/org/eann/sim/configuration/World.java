package org.eann.sim.configuration;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by martin on 17.03.17.
 */
@XmlElement
public class World {
    private int width;
    private int length;
    private int height;

    public World() {
        World(300, 300, 100);
    }

    public World(int width, int length, int height) {
        this.width = width;
        this.length = length;
        this.height = height;
    }
}
