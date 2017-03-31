package org.eann.sim.simulation.mapgeneration;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Created by martin on 18.03.17.
 */
public class AbstractHeightArrayFactoryTest {

    @Test
    public void diamondSquareFactory() {
        final AbstractHeightArrayFactory factory = new DiamondSquareFactory(1.1);
        testFactory(factory);
    }

    @Test
    public void perlinNoiseFactory() {
        final AbstractHeightArrayFactory factory = new PerlinNoiseFactory();
        testFactory(factory);
    }

    @SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.JUnitTestContainsTooManyAsserts"})
    private void testFactory(final AbstractHeightArrayFactory factory) {
        final int width = 9;
        final int length = 9;
        final double [][] map = factory.buildHeightMap(width, length);

        assertSame(width, map.length, "first dimension is not 10");
        assertSame(length, map[0].length, "2nd dimension is not 10");

        for (int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                assertThat(map[i][j]).isBetween(0.0, 1.0);
            }
        }

    }
}
