package org.eann.sim.configuration;

import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXB;
import java.io.File;

/**
 * Created by martin on 17.03.17.
 */
public class ConfigTest {

    @Test
    public final void serialization() throws Exception {
        JAXB.marshal(new Config(), new File("target/configuration.xml"));
    }
}
