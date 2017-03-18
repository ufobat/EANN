package org.eann.sim.ui;

import org.eann.sim.ui.actions.NewMapAction;
import org.eann.sim.ui.actions.ZoomInAction;
import org.eann.sim.ui.actions.ZoomOutAction;
import org.eann.sim.ui.actions.ZoomResetAction;

import javax.swing.*;

/**
 * Created by martin on 17.03.17.
 */
public class MainFrame extends JFrame {
    private JMenuBar menubar;
    private JSplitPane splitPane;
    private JScrollPane worldScrollPane;
    private WorldPanel worldpanel;
    private JTextPane halloWeltTextPane;

    public MainFrame() {
        super("bla");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        splitPane = new JSplitPane();
        splitPane.setLeftComponent(new JButton("foo"));
        worldpanel = new WorldPanel();
        worldScrollPane = new JScrollPane(worldpanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        worldScrollPane.setViewportView(worldpanel);
        splitPane.setRightComponent(worldScrollPane);
        getContentPane().add(splitPane);

        this.setupMenu();
        pack();
    }

    private void setupMenu() {
        menubar = new JMenuBar();
        JMenu filemenu = new JMenu("File");
        JMenuItem newMap = new JMenuItem(new NewMapAction(this.worldpanel));
        JMenuItem zoomIn = new JMenuItem(new ZoomInAction(this.worldpanel));
        JMenuItem zoomOut = new JMenuItem(new ZoomOutAction(this.worldpanel));
        JMenuItem zoomReset = new JMenuItem(new ZoomResetAction(this.worldpanel));
        filemenu.add( newMap );
        filemenu.add (zoomIn );
        filemenu.add (zoomOut );
        filemenu.add (zoomReset );
        menubar.add(filemenu);
        setJMenuBar(menubar);

    }
}
