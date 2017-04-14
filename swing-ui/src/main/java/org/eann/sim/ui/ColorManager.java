package org.eann.sim.ui;

import java.awt.Color;
import java.util.*;

/**
 * Created by martin on 29.03.17.
 */
public class ColorManager {
    private final Map<UUID, Color> colorTable;
    private final Set<UUID> uuidSet;
    private final List<Color> availableColors;

    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public ColorManager() {
        this.colorTable = new HashMap();
        this.uuidSet = new HashSet();
        this.availableColors = new ArrayList();
        this.initializeColors();
    }

    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    private void initializeColors() {
        for (int r = 0; r <= 255; r = r + 16) {
            for (int g = 0; g <= 255; g = g + 16) {
                for (int b = 0; b <= 255; b = b + 16) {
                    if (!(r == g && r == b)) {
                        final Color color = new Color(r, g, b);
                        this.availableColors.add(color);
                    }
                }
            }
        }
        Collections.shuffle(this.availableColors);
    }

    private Color nextColor() {
        return this.availableColors.remove(0);
    }

    public Color colorForUUID(final UUID uuid) {
        if (! this.colorTable.containsKey(uuid)) {
            this.colorTable.put(uuid, this.nextColor());
        }
        return this.colorTable.get(uuid);
    }

    public void startTransaction() {
        this.uuidSet.clear();
    }

    public void endTransaction() {
        for (final UUID uuid: this.uuidSet) {
            if (! this.colorTable.containsKey(uuid)) {
                final Color color = this.colorTable.remove(uuid);
                this.availableColors.add(color);
            }
        }
    }
}
