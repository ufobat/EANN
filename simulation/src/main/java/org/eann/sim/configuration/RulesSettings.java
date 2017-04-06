package org.eann.sim.configuration;

/**
 * Created by martin on 06.04.17.
 */
public class RulesSettings {

    private double growFoodAmount;

    public RulesSettings(final RulesSettings rulesSettings) {
        this(rulesSettings.getGrowFoodAmount());
    }

    public RulesSettings() {
        this(0.1);
    }

    public RulesSettings(final double growFoodAmount) {
        this.growFoodAmount = growFoodAmount;
    }

    public double getGrowFoodAmount() {
        return this.growFoodAmount;
    }

}
