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
    private double birthEnergy;
    private double foodToEnergy;

    public RulesSettings(final RulesSettings rulesSettings) {
        this(
                rulesSettings.getGrowFoodAmount(),
                rulesSettings.getSpawnLimit(),
                rulesSettings.getExtraSpawns(),
                rulesSettings.getRoundEnergyLoss(),
                rulesSettings.getAgeImpactFactor(),
                rulesSettings.getEatEnergyLoss(),
                rulesSettings.getSpeedEnergyLoss(),
                rulesSettings.getBirthEnergy(),
                rulesSettings.getFoodToEnergy()
        );
    }

    public RulesSettings() {
        this(0.1, 100, 0, 5, 0.007, 1, 1, 150, 70);
    }

    public RulesSettings(final double growFoodAmount, final int spawnLimit, final int extraSpawns, final double roundEnergyLoss, final double ageImpactFactor, final double eatEnergyLoss, final double speedEnergyLoss, final double birthEnergy, final double foodToEnergy) {
        this.growFoodAmount = growFoodAmount;
        this.spawnLimit = spawnLimit;
        this.extraSpawns = extraSpawns;
        this.roundEnergyLoss = roundEnergyLoss;
        this.ageImpactFactor = ageImpactFactor;
        this.eatEnergyLoss = eatEnergyLoss;
        this.speedEnergyLoss = speedEnergyLoss;
        this.birthEnergy = birthEnergy;
        this.foodToEnergy = foodToEnergy;
    }

    public void applyConfiguration(final RulesSettings rulesSettings) {
        rulesSettings.setAgeImpactFactor(rulesSettings.getAgeImpactFactor());
        rulesSettings.setBirthEnergy(rulesSettings.getBirthEnergy());
        rulesSettings.setEatEnergyLoss(rulesSettings.getEatEnergyLoss());
        rulesSettings.setExtraSpawns(rulesSettings.getExtraSpawns());
        rulesSettings.setFoodToEnergy(rulesSettings.getFoodToEnergy());
        rulesSettings.setGrowFoodAmount(rulesSettings.getGrowFoodAmount());
        rulesSettings.setRoundEnergyLoss(rulesSettings.getRoundEnergyLoss());
        rulesSettings.setSpawnLimit(rulesSettings.getSpawnLimit());
        rulesSettings.setSpeedEnergyLoss(rulesSettings.getSpeedEnergyLoss());
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

    public double getBirthEnergy() {
        return this.birthEnergy;
    }

    public double getFoodToEnergy() {
        return this.foodToEnergy;
    }

    public void setExtraSpawns(final int extraSpawns) {
        this.extraSpawns = extraSpawns;
    }

    public void setSpawnLimit(final int spawnLimit) {
        this.spawnLimit = spawnLimit;
    }

    public void setEatEnergyLoss(final double eatEnergyLoss) {
        this.eatEnergyLoss = eatEnergyLoss;
    }

    public void setRoundEnergyLoss(final double roundEnergyLoss) {
        this.roundEnergyLoss = roundEnergyLoss;
    }

    public void setSpeedEnergyLoss(final double speedEnergyLoss) {
        this.speedEnergyLoss = speedEnergyLoss;
    }

    public void setGrowFoodAmount(final double growFoodAmount) {
        this.growFoodAmount = growFoodAmount;
    }

    public void setFoodToEnergy(final double foodToEnergy) {
        this.foodToEnergy = foodToEnergy;
        }

    public void setBirthEnergy(final double birthEnergy) {
        this.birthEnergy = birthEnergy;
    }

    public void setAgeImpactFactor(final double ageImpactFactor) {
        this.ageImpactFactor = ageImpactFactor;
    }
}
