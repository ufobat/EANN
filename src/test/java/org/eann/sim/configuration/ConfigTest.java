package org.eann.sim.configuration;

import java.io.File;

import javax.xml.bind.JAXB;

import org.junit.Test;

/**
 * Created by martin on 17.03.17.
 */
public class ConfigTest {

    @Test
    public void serialization() throws Exception {
        JAXB.marshal(new Config(), new File("target/configuration.xml"));
    }
}