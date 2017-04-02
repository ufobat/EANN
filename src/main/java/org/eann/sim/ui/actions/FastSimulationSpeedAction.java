package org.eann.sim.ui.actions;

import org.eann.sim.ui.MainFrame;
import java.awt.event.ActionEvent;

/**
 * Created by martin on 02.04.17.
 */
public class FastSimulationSpeedAction extends AbstractMainFrameAction {
    public FastSimulationSpeedAction(final MainFrame mainFrame) {
        super("Fast Speed", mainFrame);
    }

    @Override
    public void actionPerformed(final ActionEvent actionEvent) {
        this.mainframe.getSimulation().setSleep(0);
    }
}

