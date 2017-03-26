package org.eann.sim.simulation.mapgeneration;

import org.junit.Test;
import static junit.framework.TestCase.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Created by martin on 18.03.17.
 */
public class HeightArrayFactoryTest {

    @Test
    public void diamondSquareFactory() {
        final HeightArrayFactory factory = new DiamondSquareFactory(1.1);
        testFactory(factory);
    }

    @Test
    public void perlinNoiseFactory() {
        final HeightArrayFactory factory = new PerlinNoiseFactory();
        testFactory(factory);
    }

    private void testFactory(HeightArrayFactory factory) {
        final int width = 9;
        final int length = 9;
        final double [][] map = factory.buildHeightMap(width, length);

        assertSame("first dimension is 10", width, map.length);
        assertSame("2nd dimension is 10", length, map[0].length);

        for (int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                assertTrue("height on " + i + ", " + j + " is <= 1 : " + map[i][j], map[i][j] <= 1);
                assertTrue("height on " + i + ", " + j + " is >= 0 : " + map[i][j], map[i][j] >= 0);
            }
        }

    }
}
