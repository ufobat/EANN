package org.eann.sim.ui;

import org.eann.sim.configuration.Config;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * Created by martin on 01.04.17.
 */
public class EditSettingsDialog extends JDialog {

    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public EditSettingsDialog(final Config configuration) {
        super();

        final JTabbedPane tabbedPane = new JTabbedPane();
        final TableModel worldTableModel = new WorldConfigTableModel(configuration.getWorld());
        final JTable worldTable = new JTable(worldTableModel);

        tabbedPane.addTab("World", new JScrollPane(worldTable));
        this.add(tabbedPane);

        this.setPreferredSize(new Dimension(400, 200));
        this.setModal(false);
        this.pack();
    }
}
