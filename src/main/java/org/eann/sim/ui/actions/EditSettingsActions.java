package org.eann.sim.ui.actions;

import org.eann.sim.configuration.Config;
import org.eann.sim.ui.EditSettingsDialog;
import org.eann.sim.ui.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by martin on 01.04.17.
 */
public class EditSettingsActions extends AbstractWorldPanelAction  {

    public EditSettingsActions(final MainFrame mainframe) {
        super("Edit Settings", mainframe);
    }

    @Override
    public void actionPerformed(final ActionEvent actionEvent) {
        final Config configuration = this.mainframe.getConfiguration();
        final Config clonedConfig = new Config(configuration);
        final JDialog settingsDialog = new EditSettingsDialog(clonedConfig);
        settingsDialog.setLocationRelativeTo(this.mainframe);
        settingsDialog.setVisible(true);
    }
}
