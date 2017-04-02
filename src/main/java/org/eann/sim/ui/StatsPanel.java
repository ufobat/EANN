package org.eann.sim.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by martin on 02.04.17.
 */
public class StatsPanel extends JPanel {

    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    StatsPanel(final MainFrame mainFrame) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setPreferredSize(new Dimension(100, 100));
        this.add(new JLabel("Statistics:"));
        final JTable statsTable = new JTable(new StatsTableModel(mainFrame));
        this.add(statsTable);
    }
}
