package org.eann.sim.ui;

import javax.swing.*;

/**
 * Created by martin on 17.03.17.
 */
public class Gui {

    public static void main(final String... argv) {
        MainFrame gui = new MainFrame();
        SwingUtilities.invokeLater(() -> gui.setVisible(true));
    }
}

