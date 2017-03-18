package org.eann.sim.ui.actions;

import org.eann.sim.ui.WorldPanel;

import javax.swing.*;

/**
 * Created by martin on 18.03.17.
 */
public abstract class AbstractWorldPanelAction extends AbstractAction {
    protected WorldPanel worldpanel;

    public AbstractWorldPanelAction(String s, WorldPanel worldPanel) {
        super(s);
        this.worldpanel = worldPanel;
    }

    public AbstractWorldPanelAction(String s) {
        super(s);
    }

    protected void updateUI() {
        SwingUtilities.invokeLater(() -> {
            worldpanel.revalidate();
            worldpanel.repaint();
        });
    }
}
