package org.eann.sim.ui.actions;

import org.eann.sim.ui.MainFrame;
import org.eann.sim.ui.WorldPanel;

import javax.swing.*;

/**
 * Created by martin on 18.03.17.
 */
public abstract class AbstractWorldPanelAction extends AbstractAction {
    final MainFrame mainframe;

    public AbstractWorldPanelAction(String s, MainFrame mainframe) {
        super(s);
        this.mainframe = mainframe;
    }

    protected void updateUI() {
        SwingUtilities.invokeLater(() -> {
            this.mainframe.getWorldpanel().revalidate();
            this.mainframe.getWorldpanel().repaint();
        });
    }
}
