package org.eann.sim.ui.actions;

import org.eann.sim.ui.MainFrame;
import javax.swing.*;

/**
 * Created by martin on 18.03.17.
 */
public abstract class AbstractWorldPanelAction extends AbstractAction {
    protected final MainFrame mainframe;

    public AbstractWorldPanelAction(final String s, final MainFrame mainframe) {
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
