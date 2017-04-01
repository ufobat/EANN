package org.eann.sim.ui;

import org.eann.sim.configuration.Config;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * Created by martin on 01.04.17.
 */
public class EditSettingsDialog extends JDialog {
    final Config config;
    final JTabbedPane tabbedPane;

    public EditSettingsDialog(Config configuration) {
        this.config = configuration;
        this.tabbedPane = new JTabbedPane();
        this.add(this.tabbedPane);

        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.black);
        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.blue);


        TableModel worldTableModel = new WorldConfigTableModel(this.config.getWorld());
        JTable worldTable = new JTable(worldTableModel);


        this.tabbedPane.addTab("World", new JScrollPane(worldTable));
        this.tabbedPane.addTab("Simulation", panel2);
        // this.tabbedPane.addTab("Creature", panel3);
        this.setSize(100, 100);
        this.setModal(false);
    }
}
