package org.eann.sim.configuration;

import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by martin on 17.03.17.
 */
public class ConfigTest {

    private JAXBContext context;

    @Before
    public void setUp() throws Exception {
        this.context = JAXBContext.newInstance(Config.class);


    }

    @Test
    public void serialization() throws Exception {
        Marshaller marshaller = this.context.createMarshaller();
        Config config = new Config();
        File file = new File("configuration.xml");
        marshaller.marshal(config, file);

    }
}