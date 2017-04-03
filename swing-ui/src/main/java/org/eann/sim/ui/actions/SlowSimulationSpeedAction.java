package org.eann.sim.ui.actions;

import org.eann.sim.ui.MainFrame;
import java.awt.event.ActionEvent;

/**
 * Created by martin on 02.04.17.
 */
public class SlowSimulationSpeedAction extends AbstractMainFrameAction {
    public SlowSimulationSpeedAction(final MainFrame mainFrame) {
        super("Slow Speed", mainFrame);
    }

    @Override
    public void actionPerformed(final ActionEvent actionEvent) {
        this.mainframe.getSimulation().setSleep(1000);
    }
}
