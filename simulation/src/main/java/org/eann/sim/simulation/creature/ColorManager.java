package org.eann.sim.simulation.creature;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Created by martin on 29.03.17.
 */
public class ColorManager {
    private final Map<Color, Integer> colorTable;
    private final List<Color> availableColors;

    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    ColorManager() {
        this.colorTable = new HashMap<Color, Integer>();
        this.availableColors = new ArrayList<Color>();
        this.initializeColors();
    }

    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    private void initializeColors() {
        for (int r = 0; r <= 255; r = r + 16) {
            for (int g = 0; g <= 255; g = g + 16) {
                for (int b = 0; b <= 255; b = b + 16) {
                    if (!(r == g && r == b)) {
                        final Color color = new Color(r, g, b);
                        this.colorTable.put(color, 0);
                        this.availableColors.add(color);
                    }
                }
            }
        }
        Collections.shuffle(this.availableColors);
    }

    public Color nextColor() {
        final Color color = this.availableColors.remove(0);
        this.colorTable.put(color, Integer.valueOf(1));
        return color;
    }

    public void decColor(final Color color) {
        final Integer count = this.colorTable.get(color);
        if (count.intValue() == 1) {
            this.availableColors.add(color);
        }
        this.colorTable.put(color, Integer.valueOf(count.intValue() - 1));
    }

    public void incColor(final Color color) {
        final Integer count = this.colorTable.get(color);
        this.colorTable.put(color, Integer.valueOf(count.intValue() + 1));
    }
}
