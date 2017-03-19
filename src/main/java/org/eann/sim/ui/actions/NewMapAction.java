package org.eann.sim.ui.actions;

import org.eann.sim.configuration.Config;
import org.eann.sim.simulation.Map;
import org.eann.sim.simulation.MapFactory;
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
            Map map = new MapFactory(new Config().getWorld()).buildWorld();
            worldpanel.setMap(map);
            this.updateUI();
        }).start();
    }
}
