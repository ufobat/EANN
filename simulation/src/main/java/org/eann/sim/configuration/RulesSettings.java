package org.eann.sim.configuration;

/**
 * Created by martin on 06.04.17.
 */
public class RulesSettings {

    private double growFoodAmount;
    private int spawnLimit;
    private int extraSpawns;
    private double roundEnergyLoss;
    private double ageImpactFactor;
    private double eatEnergyLoss;
    private double speedEnergyLoss;

    public RulesSettings(final RulesSettings rulesSettings) {
        this(
                rulesSettings.getGrowFoodAmount(),
                rulesSettings.getSpawnLimit(),
                rulesSettings.getExtraSpawns(),
                rulesSettings.getRoundEnergyLoss(),
                rulesSettings.getAgeImpactFactor(),
                rulesSettings.getEatEnergyLoss(),
                rulesSettings.getSpeedEnergyLoss()
        );
    }

    public RulesSettings() {
        this(0.1, 100, 0, 5, 0.007, 1, 1);
    }

    public RulesSettings(final double growFoodAmount, final int spawnLimit, final int extraSpawns, final double roundEnergyLoss, final double ageImpactFactor, final double eatEnergyLoss, final double speedEnergyLoss) {
        this.growFoodAmount = growFoodAmount;
        this.spawnLimit = spawnLimit;
        this.extraSpawns = extraSpawns;
        this.roundEnergyLoss = roundEnergyLoss;
        this.ageImpactFactor = ageImpactFactor;
        this.eatEnergyLoss = eatEnergyLoss;
        this.speedEnergyLoss = speedEnergyLoss;
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

    public double getRoundEnergyLoss() {
        return this.roundEnergyLoss;
    }

    public double getEatEnergyLoss() {
        return this.eatEnergyLoss;
    }

    public double getSpeedEnergyLoss() {
        return this.speedEnergyLoss;
    }

    public double getAgeImpactFactor() {
        return this.ageImpactFactor;
    }
}
