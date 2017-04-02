package org.eann.sim.ui.settings.actions;

import org.eann.sim.ui.MainFrame;
import org.eann.sim.ui.actions.AbstractMainFrameAction;
import org.eann.sim.ui.settings.EditSettingsDialog;

import java.awt.event.ActionEvent;

/**
 * Created by martin on 02.04.17.
 */
public class SaveSettingsAction extends AbstractMainFrameAction {

    final private EditSettingsDialog dialog;

    public SaveSettingsAction(final MainFrame mainframe, final EditSettingsDialog dialog) {
        super("Save", mainframe);
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(final ActionEvent actionEvent) {
        new Thread(() -> {
            this.mainframe.setConfiguration(this.dialog.getConfiguration());
            this.dialog.dispose();
        }).start();
    }
}
