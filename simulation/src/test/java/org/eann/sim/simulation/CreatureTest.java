package org.eann.sim.simulation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Created by martin on 19.03.17.
 */
public class CreatureTest {

    @Test
    @SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.JUnitTestContainsTooManyAsserts"})
    public void construction() {
        final Creature c = new Creature();
        assertTrue(c.getBodyRadius() > 0, "Creature has no size. Expected to have at least a size. Actual size: " + c.getBodyRadius());
    }

}
