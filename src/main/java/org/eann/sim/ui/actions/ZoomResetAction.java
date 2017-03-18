package org.eann.sim.ui.actions;

import org.eann.sim.ui.WorldPanel;

import java.awt.event.ActionEvent;

/**
 * Created by martin on 18.03.17.
 */
public class ZoomResetAction extends AbstractWorldPanelAction {

    public ZoomResetAction(WorldPanel worldpanel) {
        super("Reset Zoom");
        this.worldpanel = worldpanel;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        new Thread(() -> {
            worldpanel.setZoomLevel(1);
            this.updateUI();
        }).start();
    }

}
