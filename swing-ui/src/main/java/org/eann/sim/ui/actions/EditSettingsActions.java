package org.eann.sim.ui.actions;

import org.eann.sim.ui.settings.EditSettingsDialog;
import org.eann.sim.ui.MainFrame;
import javax.swing.JDialog;
import java.awt.event.ActionEvent;

/**
 * Created by martin on 01.04.17.
 */
public class EditSettingsActions extends AbstractMainFrameAction {

    public EditSettingsActions(final MainFrame mainframe) {
        super("Edit Settings", mainframe);
    }

    @Override
    public void actionPerformed(final ActionEvent actionEvent) {
        final JDialog settingsDialog = new EditSettingsDialog(this.mainframe);
        settingsDialog.setLocationRelativeTo(this.mainframe);
        settingsDialog.setVisible(true);
    }
}
