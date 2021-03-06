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
    private double maxSpeedImpact;

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
                rulesSettings.getFoodToEnergy(),
                rulesSettings.getMaxSpeedImpact()
        );
    }
    public RulesSettings() {
        this(0.1, 100, 0, 5, 0.007, 1, 1, 150, 70, 0.1);
    }

    @SuppressWarnings("PMD.ExcessiveParameterList")
    public RulesSettings(final double growFoodAmount, final int spawnLimit, final int extraSpawns, final double roundEnergyLoss, final double ageImpactFactor, final double eatEnergyLoss, final double speedEnergyLoss, final double birthEnergy, final double foodToEnergy, final double maxSpeedImpact) {
        this.growFoodAmount = growFoodAmount;
        this.spawnLimit = spawnLimit;
        this.extraSpawns = extraSpawns;
        this.roundEnergyLoss = roundEnergyLoss;
        this.ageImpactFactor = ageImpactFactor;
        this.eatEnergyLoss = eatEnergyLoss;
        this.speedEnergyLoss = speedEnergyLoss;
        this.birthEnergy = birthEnergy;
        this.foodToEnergy = foodToEnergy;
        this.maxSpeedImpact = maxSpeedImpact;
    }

    public void applyConfiguration(final RulesSettings rulesSettings) {
        this.setAgeImpactFactor(rulesSettings.getAgeImpactFactor());
        this.setBirthEnergy(rulesSettings.getBirthEnergy());
        this.setEatEnergyLoss(rulesSettings.getEatEnergyLoss());
        this.setExtraSpawns(rulesSettings.getExtraSpawns());
        this.setFoodToEnergy(rulesSettings.getFoodToEnergy());
        this.setGrowFoodAmount(rulesSettings.getGrowFoodAmount());
        this.setRoundEnergyLoss(rulesSettings.getRoundEnergyLoss());
        this.setSpawnLimit(rulesSettings.getSpawnLimit());
        this.setSpeedEnergyLoss(rulesSettings.getSpeedEnergyLoss());
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

    public double getMaxSpeedImpact() {
        return this.maxSpeedImpact;
    }

    public void setMaxSpeedImpact(final double maxSpeedImpact) {
        this.maxSpeedImpact = maxSpeedImpact;
    }
}
