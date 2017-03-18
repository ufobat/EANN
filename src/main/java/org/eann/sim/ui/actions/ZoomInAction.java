package org.eann.sim.ui.actions;

import org.eann.sim.ui.WorldPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by martin on 18.03.17.
 */
public class ZoomInAction extends AbstractWorldPanelAction {
    public ZoomInAction(WorldPanel worldpanel) {
        super("Zoom In");
        this.worldpanel = worldpanel;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        new Thread(() -> {
            worldpanel.setZoomLevel( worldpanel.getZoomLevel() * 1.5 );
            this.updateUI();
        }).start();
    }
}
