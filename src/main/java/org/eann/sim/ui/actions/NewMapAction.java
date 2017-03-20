package org.eann.sim.ui.actions;

import org.eann.sim.configuration.Config;
import org.eann.sim.simulation.Creature;
import org.eann.sim.simulation.MapFactory;
import org.eann.sim.simulation.World;
import org.eann.sim.ui.WorldPanel;

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
            World world = new MapFactory(new Config().getWorld()).buildWorld();
            Creature c = new Creature(10,10);
            world.addCreature(c);
            worldpanel.setWorld(world);
            this.updateUI();
        }).start();
    }
}
