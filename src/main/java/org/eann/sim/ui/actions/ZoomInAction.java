package org.eann.sim.ui.actions;

import org.eann.sim.ui.MainFrame;
import org.eann.sim.ui.WorldPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by martin on 18.03.17.
 */
public class ZoomInAction extends AbstractWorldPanelAction {
    public ZoomInAction(MainFrame mainframe) {
        super("Zoom In", mainframe);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        new Thread(() -> {
            WorldPanel worldpanel = this.mainframe.getWorldpanel();
            worldpanel.setZoomLevel( worldpanel.getZoomLevel() * 1.5 );
            this.updateUI();
        }).start();
    }
}
