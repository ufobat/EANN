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

    final private WorldSettings worldSettings;

    /**
     * required for JAXB
     */
    public Config() {
        this(new WorldSettings());
    }

    public Config(final WorldSettings worldSettings) {
        this.worldSettings = worldSettings;
    }

    public Config(final Config configuration) {
        this(new WorldSettings(configuration.getWorldSettings()));
    }

    public WorldSettings getWorldSettings() {
        return this.worldSettings;
    }
}
