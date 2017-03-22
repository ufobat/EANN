package org.eann.sim.ui;

import org.eann.sim.simulation.Simulation;
import org.eann.sim.ui.actions.*;

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
    private Simulation simulation;

    public MainFrame() {
        super("EANN Simulation GUI");
        this.setupGui();
        pack();
    }

    private void setupGui() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.splitPane = new JSplitPane();
        this.splitPane.setLeftComponent(new JButton("foo"));
        this.worldpanel = new WorldPanel();
        this.worldScrollPane = new JScrollPane(worldpanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.worldScrollPane.setViewportView(worldpanel);
        this.splitPane.setRightComponent(worldScrollPane);
        this.getContentPane().add(splitPane);

        this.setupMenu();
    }

    private void setupMenu() {
        menubar = new JMenuBar();
        JMenu filemenu = new JMenu("File");
        JMenuItem newMap = new JMenuItem(new NewMapAction(this));
        JMenuItem startSim = new JMenuItem(new StartSimulationAction(this));
        JMenuItem stopSim = new JMenuItem(new StopSimulationAction(this));
        JMenuItem zoomIn = new JMenuItem(new ZoomInAction(this));
        JMenuItem zoomOut = new JMenuItem(new ZoomOutAction(this));
        JMenuItem zoomReset = new JMenuItem(new ZoomResetAction(this));
        filemenu.add(newMap);
        filemenu.add(startSim);
        filemenu.add(stopSim);
        filemenu.add(zoomIn);
        filemenu.add(zoomOut);
        filemenu.add(zoomReset);
        menubar.add(filemenu);
        this.setJMenuBar(menubar);
    }

    public WorldPanel getWorldpanel() {
        return worldpanel;
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }
}
