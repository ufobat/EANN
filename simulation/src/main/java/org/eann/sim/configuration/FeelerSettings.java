package org.eann.sim.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by martin on 04.04.17.
 */
@XmlAccessorType(value= XmlAccessType.FIELD)
public class FeelerSettings {
    private int length;

    public FeelerSettings() {
        this(15);
    }
    public FeelerSettings(final int length) {
        this.length = length;
    }
    public FeelerSettings(final FeelerSettings feelerSettings) {
        this(feelerSettings.length);
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(final int length) {
        this.length = length;
    }
}
