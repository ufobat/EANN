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
        Config configuration = this.mainframe.getConfiguration();
        JDialog editSettingsDialog = new EditSettingsDialog(configuration);
        editSettingsDialog.setVisible(true);
    }
}
