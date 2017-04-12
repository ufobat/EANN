package org.eann.sim.ui.settings;

import org.eann.sim.configuration.RulesSettings;

/**
 * Created by martin on 08.04.17.
 */
@SuppressWarnings({"PMD.StdCyclomaticComplexity", "PMD.CyclomaticComplexity", "PMD.ModifiedCyclomaticComplexity"})
public class RulesConfigTableModel extends AbstractConfigTableModel {
    private final RulesSettings rulesSettings;

    public RulesConfigTableModel(final RulesSettings rulesSettings) {
        super();
        this.rulesSettings = rulesSettings;
        this.rowValues = new AbstractConfigRowValues[]{
                new AbstractConfigRowValues("Extra Spawns", "Extra Spawns if there are more Creatures then Spawn Limit defines") {
                    @Override
                    public String getValue() {
                        return Integer.toString(RulesConfigTableModel.this.rulesSettings.getExtraSpawns());
                    }

                    @Override
                    public void setValue(final Object o) {
                        RulesConfigTableModel.this.rulesSettings.setExtraSpawns(Integer.parseInt((String) o));
                    }
                },
                new AbstractConfigRowValues("Spawn Limit", "New creatures will be spawned if there are less creatures then Spawn Limit") {
                    @Override
                    public String getValue() {
                        return Integer.toString(RulesConfigTableModel.this.rulesSettings.getSpawnLimit());
                    }

                    @Override
                    public void setValue(final Object o) {
                        RulesConfigTableModel.this.rulesSettings.setSpawnLimit(Integer.parseInt((String) o));
                    }
                },
                new AbstractConfigRowValues("Age Impact Factor", "Increases the Energy Penalty Per Round based on your age. This makes you die") {
                    @Override
                    public String getValue() {
                        return Double.toString(RulesConfigTableModel.this.rulesSettings.getAgeImpactFactor());
                    }

                    @Override
                    public void setValue(final Object o) {
                        RulesConfigTableModel.this.rulesSettings.setAgeImpactFactor(Double.parseDouble((String) o));
                    }
                },
                new AbstractConfigRowValues("Birth Energy", "The Energy of af a new CreatureState") {
                    @Override
                    public String getValue() {
                        return Double.toString(RulesConfigTableModel.this.rulesSettings.getBirthEnergy());
                    }

                    @Override
                    public void setValue(final Object o) {
                        RulesConfigTableModel.this.rulesSettings.setBirthEnergy(Double.parseDouble((String) o));
                    }
                },
                new AbstractConfigRowValues("Food to Energy", "When eating this is the maxim Energy that can be restored") {
                    @Override
                    public String getValue() {
                        return Double.toString(RulesConfigTableModel.this.rulesSettings.getFoodToEnergy());
                    }

                    @Override
                    public void setValue(final Object o) {
                        RulesConfigTableModel.this.rulesSettings.setFoodToEnergy(Double.parseDouble((String) o));
                    }
                },
                new AbstractConfigRowValues("Grow Food Amount", "How Much Food can a tile produce per round") {
                    @Override
                    public String getValue() {
                        return Double.toString(RulesConfigTableModel.this.rulesSettings.getGrowFoodAmount());
                    }

                    @Override
                    public void setValue(final Object o) {
                        RulesConfigTableModel.this.rulesSettings.setGrowFoodAmount(Double.parseDouble((String) o));
                    }
                },
                new AbstractConfigRowValues("Speed Energy Loss", "Defines the Energy Loss from moving around") {
                    @Override
                    public String getValue() {
                        return Double.toString(RulesConfigTableModel.this.rulesSettings.getSpeedEnergyLoss());
                    }

                    @Override
                    public void setValue(final Object o) {
                        RulesConfigTableModel.this.rulesSettings.setSpeedEnergyLoss(Double.parseDouble((String) o));
                    }
                },
                new AbstractConfigRowValues("Round Energy Loss", "Defines a Constant Energy Loss Per Round") {
                    @Override
                    public String getValue() {
                        return Double.toString(RulesConfigTableModel.this.rulesSettings.getRoundEnergyLoss());
                    }

                    @Override
                    public void setValue(final Object o) {
                        RulesConfigTableModel.this.rulesSettings.setRoundEnergyLoss(Double.parseDouble((String) o));
                    }
                },
                new AbstractConfigRowValues("Eat Energy Loss", "Defines the Energy Loss for trying to eat") {
                    @Override
                    public String getValue() {
                        return Double.toString(RulesConfigTableModel.this.rulesSettings.getEatEnergyLoss());
                    }

                    @Override
                    public void setValue(final Object o) {
                        RulesConfigTableModel.this.rulesSettings.setEatEnergyLoss(Double.parseDouble((String) o));
                    }
                }
        };
    }
}
