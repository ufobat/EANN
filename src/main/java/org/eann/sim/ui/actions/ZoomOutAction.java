package org.eann.sim.ui.actions;

import org.eann.sim.ui.WorldPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by martin on 18.03.17.
 */
public class ZoomOutAction extends AbstractWorldPanelAction {
    public ZoomOutAction(WorldPanel worldpanel) {
        super("Zoom Out");
        this.worldpanel = worldpanel;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        new Thread(() -> {
            worldpanel.setZoomLevel( worldpanel.getZoomLevel() / 1.5 );
            this.updateUI();
        }).start();
    }
}
