package org.eann.sim.ui.actions;

import org.eann.sim.ui.MainFrame;
import java.awt.event.ActionEvent;

/**
 * Created by martin on 18.03.17.
 */
public class ZoomResetAction extends AbstractWorldPanelAction {

    public ZoomResetAction(final MainFrame mainframe) {
        super("Reset Zoom", mainframe);
    }

    @Override
    public void actionPerformed(final ActionEvent actionEvent) {
        new Thread(() -> {
            this.mainframe.getWorldpanel().setZoomLevel(1);
            this.updateUI();
        }).start();
    }

}
