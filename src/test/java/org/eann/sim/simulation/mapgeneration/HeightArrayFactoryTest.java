package org.eann.sim.simulation.mapgeneration;

import org.junit.Test;
import static org.junit.Assert.assertTrue;


/**
 * Created by martin on 18.03.17.
 */
public class HeightArrayFactoryTest {

    @Test
    public void diamondSquareFactory() {
        HeightArrayFactory factory = new DiamondSquareFactory(1.1);
        testFactory(factory);
    }

    private void testFactory(HeightArrayFactory factory) {
        double [][] map = factory.buildHeightMap(10, 10);

        assertTrue("first dimension is 10", map.length == 10);
        assertTrue("2nd dimension is 10", map[0].length == 10);

        for (int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                assertTrue("height on " + i + ", " + j + " is <= 1 : " + map[i][j], map[i][j] <= 1);
                assertTrue("height on " + i + ", " + j + " is <= 1 : " + map[i][j], map[i][j] >= 0);
            }
        }

    }
}
