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

    private World world = new World();

    // required for JAXB
    public Config() {

    }

    public Config(World world) {
        this.world = world;
    }
}
