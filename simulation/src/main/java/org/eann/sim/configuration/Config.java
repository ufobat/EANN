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
    final private CreatureSettings creatureSettings;
    final private RulesSettings rulesSettings;

    /**
     * required for JAXB
     */
    public Config() {
        this(new WorldSettings(), new CreatureSettings(), new RulesSettings());

    }

    public Config(final WorldSettings worldSettings, final CreatureSettings creatureSettings, final RulesSettings rulesSettings) {
        this.worldSettings = worldSettings;
        this.creatureSettings = creatureSettings;
        this.rulesSettings = rulesSettings;
    }

    public Config(final Config configuration) {
        this(new WorldSettings(configuration.getWorldSettings()), new CreatureSettings(configuration.getCreatureSettings()), new RulesSettings(configuration.getRulesSettings()));
    }

    public RulesSettings getRulesSettings() {
        return  this.rulesSettings;
    }

    public WorldSettings getWorldSettings() {
        return this.worldSettings;
    }

    public CreatureSettings getCreatureSettings() {
        return this.creatureSettings;
    }

}
