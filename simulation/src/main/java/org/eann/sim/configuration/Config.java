package org.eann.sim.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by martin on 17.03.17.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Config {

    private World world;

    /**
     * required for JAXB
     */
    public Config() {
        this(new World());
    }

    public Config(final World world) {
        this.world = world;
    }

    public Config(final Config configuration) {
        this(new World(configuration.getWorld()));
    }

    public World getWorld() {
        return this.world;
    }
}
