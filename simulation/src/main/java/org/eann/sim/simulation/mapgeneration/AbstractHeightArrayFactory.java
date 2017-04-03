package org.eann.sim.simulation.mapgeneration;

/**
 * Created by martin on 18.03.17.
 */
public abstract class AbstractHeightArrayFactory {
    public abstract double[][] buildHeightMap(int width, int length);

    @SuppressWarnings("PMD.UseVarargs")
    protected void normalize(double[][] array) {
        double min = array[0][0];
        double max = array[0][0];

        for (int i = 0; i < array.length; i++) {
            for(int j = 0; j < array[i].length; j++) {
                min = Math.min(min, array[i][j]);
                max = Math.max(max, array[i][j]);
            }
        }

        for (int i = 0; i < array.length; i++) {
            for(int j = 0; j < array[i].length; j++) {
                final double value = array[i][j];
                array[i][j] = (value - min) / (max - min);
            }
        }
    }
}
