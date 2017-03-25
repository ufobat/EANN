package org.eann.sim.simulation;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by martin on 25.03.17.
 */
public class TileTest {

    @Test
    public void foodLevelTests() {
        Tile t = new Tile(0.0, 0, 0);
        assertTrue("food level is minmal", t.getFoodLevel() == 0);

        t.growFood();
        assertTrue("more food", t.getFoodLevel() != 0);

        double level = t.getFoodLevel();
        double ate = t.reduceFoodLevel(1f);
        assertTrue("ate all available food", ate == level);

        assertTrue("food level is minmal", t.getFoodLevel() == 0);
        double ateAgain = t.reduceFoodLevel(1f);
        assertTrue(ateAgain == 0);

        boolean caughtException = false;
        try {
            t.reduceFoodLevel(-0.4);
        } catch(IllegalArgumentException exception) {
            caughtException = true;
        }
        assertTrue("caught exception", caughtException);

        t.growFood();
        level = t.getFoodLevel();
        t.reduceFoodLevel(level/3);
        double newLevel = t.getFoodLevel();
        assertTrue("ate just a bit", Math.abs(newLevel - (level/3 * 2)) < 0.0001);
    }

}
