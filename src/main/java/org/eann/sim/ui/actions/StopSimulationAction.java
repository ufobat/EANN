package org.eann.sim.ui.actions;

import org.eann.sim.ui.MainFrame;

import java.awt.event.ActionEvent;

/**
 * Created by martin on 22.03.17.
 */
public class StopSimulationAction extends AbstractWorldPanelAction {

    public StopSimulationAction(MainFrame mainframe) {
        super("Stop Simulation", mainframe);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        this.mainframe.getSimulation().setPauseSimulation(true);
    }
}
