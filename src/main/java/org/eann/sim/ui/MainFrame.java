package org.eann.sim.ui;

import org.eann.sim.configuration.Config;
import org.eann.sim.simulation.World;
import org.eann.sim.simulation.WorldFactory;

import javax.swing.*;

/**
 * Created by martin on 17.03.17.
 */
public class MainFrame extends JFrame {
    private JMenuBar menubar;
    private JSplitPane splitPane;
    private WorldPanel worldpanel;
    private JTextPane halloWeltTextPane;

    public MainFrame() {
        super("bla");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        splitPane = new JSplitPane();
        splitPane.setLeftComponent(new JButton("foo"));
        worldpanel = new WorldPanel();
        splitPane.setRightComponent(worldpanel);
        getContentPane().add(splitPane);

        this.setupMenu();
        pack();
    }

    private void setupMenu() {
        menubar = new JMenuBar();
        JMenu filemenu = new JMenu("File");
        JMenuItem newMap = new JMenuItem(new NewMapAction(this.worldpanel));
        filemenu.add( newMap );
        menubar.add(filemenu);
        setJMenuBar(menubar);

    }
}
