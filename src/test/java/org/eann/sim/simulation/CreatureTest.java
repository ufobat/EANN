package org.eann.sim.simulation;

import org.junit.Test;
import static org.junit.Assert.assertTrue;


/**
 * Created by martin on 19.03.17.
 */
public class CreatureTest {

    @Test
    @SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.JUnitTestContainsTooManyAsserts"})
    public void construction() {
        final Creature c = new Creature();
        assertTrue("Creature has a certain size: ", c.getBodyRadius() > 0);
    }

}
