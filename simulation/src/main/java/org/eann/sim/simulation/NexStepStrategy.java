package org.eann.sim.simulation;

import org.eann.sim.configuration.Config;

/**
 * Created by martin on 11.04.17.
 */
public interface NexStepStrategy {
    void calculateNextStep(final World world, final Config config);
}
