package org.eann.sim.ui;

import org.eann.sim.configuration.Config;
import org.eann.sim.simulation.Simulation;
import org.eann.sim.ui.actions.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

/**
 * Created by martin on 17.03.17.
 */
public class MainFrame extends JFrame {
    private WorldPanel worldpanel;
    private Simulation simulation;
    final private Config configuration;

    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public MainFrame() {
        super("EANN Simulation GUI");
        this.configuration = new Config();
        this.setupGui();

        final Timer timer = new Timer(100, (actionEvent) -> {
            this.worldpanel.repaint();
        });
        timer.start();

        this.pack();
    }

    private void setupGui() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.worldpanel = new WorldPanel();

        final JScrollPane worldScrollPane = new JScrollPane(worldpanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        worldScrollPane.setViewportView(worldpanel);

        final JSplitPane splitPane = new JSplitPane();
        splitPane.setRightComponent(worldScrollPane);
        this.getContentPane().add(splitPane);

        this.setupMenu();
        setPreferredSize(new Dimension(800, 600));
    }

    private void setupMenu() {
        final JMenuBar menubar = new JMenuBar();
        final JMenu filemenu = new JMenu("File");
        final JMenuItem newMap = new JMenuItem(new NewMapAction(this));
        final JMenuItem startSim = new JMenuItem(new StartSimulationAction(this));
        final JMenuItem stopSim = new JMenuItem(new StopSimulationAction(this));
        final JMenuItem zoomIn = new JMenuItem(new ZoomInAction(this));
        final JMenuItem zoomOut = new JMenuItem(new ZoomOutAction(this));
        final JMenuItem zoomReset = new JMenuItem(new ZoomResetAction(this));
        final JMenuItem editSettings = new JMenuItem( new EditSettingsActions(this));
        final JMenuItem exit = new JMenuItem(new AbstractAction("Quit") {
            @Override
            public void actionPerformed(final ActionEvent e) {
                dispatchEvent(new WindowEvent(MainFrame.this, WindowEvent.WINDOW_CLOSING));
            }
        });
        filemenu.add(newMap);
        filemenu.add(startSim);
        filemenu.add(stopSim);
        filemenu.add(editSettings);
        filemenu.add(new JSeparator());
        filemenu.add(zoomIn);
        filemenu.add(zoomOut);
        filemenu.add(zoomReset);
        filemenu.add(new JSeparator());
        filemenu.add(exit);
        menubar.add(filemenu);
        this.setJMenuBar(menubar);
    }

    public WorldPanel getWorldpanel() {
        return worldpanel;
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public void setSimulation(final Simulation simulation) {
        this.simulation = simulation;
    }

    public Config getConfiguration() {
        return this.configuration;
    }
}
