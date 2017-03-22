package org.eann.sim.ui.actions;

import org.eann.sim.ui.MainFrame;
import org.eann.sim.ui.WorldPanel;

import java.awt.event.ActionEvent;

/**
 * Created by martin on 18.03.17.
 */
public class ZoomResetAction extends AbstractWorldPanelAction {

    public ZoomResetAction(MainFrame mainframe) {
        super("Reset Zoom", mainframe);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        new Thread(() -> {
            this.mainframe.getWorldpanel().setZoomLevel(1);
            this.updateUI();
        }).start();
    }

}
