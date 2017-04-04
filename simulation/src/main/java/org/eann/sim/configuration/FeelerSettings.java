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
        this.length = 15;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(final int length) {
        this.length = length;
    }
}
