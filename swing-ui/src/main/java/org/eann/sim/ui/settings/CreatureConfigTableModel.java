package org.eann.sim.ui.settings;

import org.eann.sim.configuration.CreatureSettings;

/**
 * Created by martin on 08.04.17.
 */
@SuppressWarnings({"PMD.StdCyclomaticComplexity", "PMD.CyclomaticComplexity", "PMD.ModifiedCyclomaticComplexity"})
public class CreatureConfigTableModel extends AbstractConfigTableModel {
    private static final long serialVersionUID = 3952031866616845318L;
    private final CreatureSettings creatureSettings;

    public CreatureConfigTableModel(final CreatureSettings creatureSettings) {
        super();
        this.creatureSettings = creatureSettings;
        this.rowValues = new AbstractConfigRowValues[]{
                new AbstractConfigRowValues("Body Radius", "Defines the Size of the Creatures Body") {
                    @Override
                    public String getValue() {
                        return Integer.toString(CreatureConfigTableModel.this.creatureSettings.getBodyRadius());
                    }

                    @Override
                    public void setValue(final Object o) {
                        CreatureConfigTableModel.this.creatureSettings.setBodyRadius(Integer.parseInt((String) o));
                    }
                },
                new AbstractConfigRowValues("Neurons Per Hidden Layer", "Defines the Number of Neurons Per each hidden Layer") {
                    @Override
                    public String getValue() {
                        return Integer.toString(CreatureConfigTableModel.this.creatureSettings.getNeuronsPerHiddenLayer());
                    }

                    @Override
                    public void setValue(final Object o) {
                        CreatureConfigTableModel.this.creatureSettings.setNeuronsPerHiddenLayer(Integer.parseInt((String) o));
                    }
                },
                new AbstractConfigRowValues("Number of Hidden Layers", "Defines the Number of Hidden Layers") {
                    @Override
                    public String getValue() {
                        return Integer.toString(CreatureConfigTableModel.this.creatureSettings.getNoOfHiddenLayer());
                    }

                    @Override
                    public void setValue(final Object o) {
                        CreatureConfigTableModel.this.creatureSettings.setNoOfHiddenLayer(Integer.parseInt((String) o));
                    }
                },
                new AbstractConfigRowValues("Speed Factor", "Defines the maximum Speed of the Creature") {
                    @Override
                    public String getValue() {
                        return Double.toString(CreatureConfigTableModel.this.creatureSettings.getSpeedFactor());
                    }

                    @Override
                    public void setValue(final Object o) {
                        CreatureConfigTableModel.this.creatureSettings.setSpeedFactor(Double.parseDouble((String) o));
                    }
                },
                new AbstractConfigRowValues("FeelerState Length", "Defines the length of the feelers") {
                    @Override
                    public String getValue() {
                        return Integer.toString(CreatureConfigTableModel.this.creatureSettings.getFeelerLength());
                    }

                    @Override
                    public void setValue(final Object o) {
                        CreatureConfigTableModel.this.creatureSettings.setFeelerLength(Integer.parseInt((String) o));
                    }
                },
                new AbstractConfigRowValues("Number of Feelers", "Defines the number of Feelers") {
                    @Override
                    public String getValue() {
                        return Integer.toString(CreatureConfigTableModel.this.creatureSettings.getNoOfFeeler());
                    }

                    @Override
                    public void setValue(final Object o) {
                        CreatureConfigTableModel.this.creatureSettings.setNoOfFeeler(Integer.parseInt((String) o));
                    }
                }
        };
    }
}
