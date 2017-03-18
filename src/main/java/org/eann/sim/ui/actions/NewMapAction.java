package org.eann.sim.ui.actions;

import org.eann.sim.configuration.Config;
import org.eann.sim.simulation.World;
import org.eann.sim.simulation.WorldFactory;
import org.eann.sim.ui.WorldPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by martin on 17.03.17.
 */
public class NewMapAction extends AbstractWorldPanelAction {

    public NewMapAction(WorldPanel worldpanel) {
        super("New Map");
        this.worldpanel = worldpanel;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        new Thread(() -> {
            World world = new WorldFactory(new Config().getWorld()).buildWorld();
            worldpanel.setWorld(world);
            this.updateUI();
        }).start();
    }
}
