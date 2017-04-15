package org.eann.sim.ui.actions;

import org.eann.sim.simulation.Simulation;
import org.eann.sim.ui.MainFrame;
import java.awt.event.ActionEvent;

/**
 * Created by martin on 17.03.17.
 */
public class NewMapAction extends AbstractMainFrameAction {

    public NewMapAction(final MainFrame mainframe) {
        super("New Simulation", mainframe);
    }

    @Override
    public void actionPerformed(final ActionEvent actionEvent) {
        new Thread(() -> {
            final Simulation oldSimulation = this.mainframe.getSimulation();
            if (oldSimulation != null) {
                oldSimulation.abort();
            }
            final Simulation simulation = new Simulation(this.mainframe.getConfiguration());
            simulation.setup();
            simulation.start();
            this.mainframe.setSimulation(simulation);
            this.updateUI();
        }).start();
    }
}
