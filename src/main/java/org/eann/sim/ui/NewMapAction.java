package org.eann.sim.ui;

import org.eann.sim.configuration.Config;
import org.eann.sim.simulation.World;
import org.eann.sim.simulation.WorldFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by martin on 17.03.17.
 */
public class NewMapAction extends AbstractAction {

    private final WorldPanel worldpanel;

    protected NewMapAction(WorldPanel worldpanel) {
        super("New Map");
        this.worldpanel = worldpanel;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        new Thread() {
            @Override
            public void run() {
                World world = new WorldFactory(new Config().getWorld()).buildWorld();
                worldpanel.setWorld(world);

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        worldpanel.revalidate();
                        worldpanel.repaint();
                    }
                });
            }
        }.start();
    }
}
