package org.eann.sim.ui;

import org.eann.sim.simulation.dataexchange.Snapshot;

import javax.swing.*;
import java.awt.*;

/**
 * Created by martin on 02.04.17.
 */
public class StatsPanel extends JPanel {

    private static final long serialVersionUID = 924055497376451812L;
    private final StatsTableModel stm;

    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    StatsPanel() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setPreferredSize(new Dimension(100, 100));
        this.add(new JLabel("Statistics:"));
        this.stm = new StatsTableModel();
        final JTable statsTable = new JTable(this.stm);
        this.add(statsTable);
    }

    public void setSnapshot(final Snapshot snapshot) {
        this.stm.setSnapshot(snapshot);
    }
}
