package org.eann.sim.simulation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by martin on 25.03.17.
 */
public class TileTest {

    /**
     * Test that there is no food on a new Tile
     */
    @Test
    @SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.JUnitTestContainsTooManyAsserts"})
    public void foodLevelOnNewTile() {
        final Tile t = new Tile(0.0, 0, 0);
        assertEquals("food level is minmal", 0, t.getFoodLevel(), 0);
    }

    /**
     * Test that growing Food works.
     */
    @Test
    @SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.JUnitTestContainsTooManyAsserts"})
    public void foodLevelGrowFood() {
        final Tile t = new Tile(0.0, 0, 0);
        t.growFood();
        assertNotEquals("more food", 0, t.getFoodLevel());
    }

    /**
     * Test to eat when nothing is there
     */
    @Test
    @SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.JUnitTestContainsTooManyAsserts"})
    public void foodLevelEatNothing() {
        final Tile t = new Tile(0.0, 0, 0);
        final double ate = t.reduceFoodLevel(1f);
        assertEquals("ate nothing", 0, ate, 0);
        assertNotEquals("there is still no food", 0, t.getFoodLevel());
    }

    /**
     * Test to eat an illegal amouont of Food
     */
    @Test
    @SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.JUnitTestContainsTooManyAsserts"})
    public void foodLevelEatIllegal() {
        final Tile t = new Tile(0.0, 0, 0);

        boolean caughtException = false;
        try {
            t.reduceFoodLevel(-0.4);
        } catch(IllegalArgumentException exception) {
            caughtException = true;
        }
        assertTrue("caught exception", caughtException);
    }

    /**
     * Test to eat more than available
     */
    @Test
    @SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.JUnitTestContainsTooManyAsserts"})
    public void foodLevelEatMore() {
        final Tile t = new Tile(0.0, 0, 0);
        t.growFood();

        final double level = t.getFoodLevel();
        final double ate = t.reduceFoodLevel(1f);
        assertEquals("ate all available food", ate, level, 0);
        assertEquals("food level is minimal", 0, t.getFoodLevel(), 0);
    }

    /**
     * Test to eat less then what is available
     */
    @Test
    @SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.JUnitTestContainsTooManyAsserts"})
    public void foodLevelEatLess() {
        final Tile t = new Tile(0.0, 0, 0);
        t.growFood();

        final double level = t.getFoodLevel();
        t.reduceFoodLevel(level/3);
        final double newLevel = t.getFoodLevel();
        assertTrue("ate just a bit", Math.abs(newLevel - (level/3 * 2)) < 0.0001);
    }
}
