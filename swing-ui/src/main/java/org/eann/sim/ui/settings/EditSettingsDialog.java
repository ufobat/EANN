package org.eann.sim.ui.settings;

import org.eann.sim.configuration.Config;
import org.eann.sim.ui.MainFrame;
import org.eann.sim.ui.settings.actions.SaveSettingsAction;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by martin on 01.04.17.
 */
public class EditSettingsDialog extends JDialog {

    private final Config configuration;

    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public EditSettingsDialog(final MainFrame mainframe) {
        super();
        final Config configuration = mainframe.getConfiguration();
        final Config clonedConfig = new Config(configuration);
        this.configuration = clonedConfig;
        this.setLayout(new GridBagLayout());

        final GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.insets = new Insets(0,0,0,0);

        c.gridwidth = 3;

        final JTabbedPane tabbedPane = new JTabbedPane();
        final TableModel worldTableModel = new WorldConfigTableModel(configuration.getWorldSettings());
        final JTable worldTable = new JTable(worldTableModel);

        tabbedPane.addTab("WorldSettings", new JScrollPane(worldTable));
        this.add(tabbedPane, c);

        // next row
        c.insets = new Insets(5,5,5,5);
        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 0;
        c.weighty = 0;


        final JButton btnCancle = new JButton(new AbstractAction("Cancle") {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                EditSettingsDialog.this.dispose();
            }
        });
        final JButton btnSave = new JButton(new SaveSettingsAction(mainframe, this));

        c.gridx = 1;
        this.add(btnCancle, c);
        c.gridx = 2;
        this.add(btnSave, c);

        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(400, 200));
        this.setModal(false);
        this.pack();
    }

    public Config getConfiguration() {
        return this.configuration;
    }
}
