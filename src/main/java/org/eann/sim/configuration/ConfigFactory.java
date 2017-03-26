package org.eann.sim.configuration;

import javax.xml.bind.JAXB;
import java.io.File;

/**
 * Created by martin on 17.03.17.
 */
public class ConfigFactory
{
    public Config buildConfiguration() {
        return new Config();
    }
    public Config buildConfiguration(final File configfile){
        return JAXB.unmarshal(configfile, Config.class);
    }
    public Config buildConfiguration(final String configfile) {
        return this.buildConfiguration(new File(configfile));
    }
}
