package org.eann.sim.configuration;

/**
 * Created by martin on 06.04.17.
 */
public class RulesSettings {

    private double growFoodAmount;
    private int spawnLimit;
    private int extraSpawns;

    public RulesSettings(final RulesSettings rulesSettings) {
        this(rulesSettings.getGrowFoodAmount(), rulesSettings.getSpawnLimit(), rulesSettings.getExtraSpawns());
    }

    public RulesSettings() {
        this(0.1, 100, 0);
    }

    public RulesSettings(final double growFoodAmount, final int spawnLimit, final int extraSpawns) {
        this.growFoodAmount = growFoodAmount;
        this.spawnLimit = spawnLimit;
        this.extraSpawns = extraSpawns;
    }

    public double getGrowFoodAmount() {
        return this.growFoodAmount;
    }

    public int getSpawnLimit() {
        return this.spawnLimit;
    }

    public int getExtraSpawns() {
        return this.extraSpawns;
    }
}
