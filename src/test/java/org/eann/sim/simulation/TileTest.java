package org.eann.sim.simulation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertEquals(0, t.getFoodLevel(), 0);
    }

    /**
     * Test that growing Food works.
     */
    @Test
    @SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.JUnitTestContainsTooManyAsserts"})
    public void foodLevelGrowFood() {
        final Tile t = new Tile(0.0, 0, 0);
        t.growFood();
        assertNotEquals(0, t.getFoodLevel());
    }

    /**
     * Test to eat when nothing is there
     */
    @Test
    @SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.JUnitTestContainsTooManyAsserts"})
    public void foodLevelEatNothing() {
        final Tile t = new Tile(0.0, 0, 0);
        final double ate = t.reduceFoodLevel(1f);
        assertEquals(0, ate, 0, "ate something");
        assertNotEquals(0, t.getFoodLevel(), "there is still no food");
    }

    /**
     * Test to eat an illegal amount of Food
     */
    @Test
    @SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.JUnitTestContainsTooManyAsserts"})
    public void foodLevelEatIllegal() {
        assertThrows(IllegalArgumentException.class, () -> {
            final Tile t = new Tile(0.0, 0, 0);
            t.reduceFoodLevel(-0.4);
        });
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
        assertEquals(ate, level, 0, "not all food was eaten.");
        assertEquals(0, t.getFoodLevel(), 0, "food level should be minimal");
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
        assertTrue(Math.abs(newLevel - (level/3 * 2)) < 0.0001, "ate too much, my belly hurts");
    }
}
