package com.himmerland.hero.domain.rules;

public class RuleThresholdHeat extends Rule {

    private int thresholdTempIn;
    private int thresholdTempOut;
    private int thresholdWaterFlow;

    public RuleThresholdHeat() {
        
    } // <-- Jackson needs a no-arg constructor

    public RuleThresholdHeat(String name, String description, String consumptionsType, int duration, int thresholdTempIn, int thresholdTempOut, int thresholdWaterFlow) {
        super(name, description, consumptionsType, duration);
        this.thresholdTempIn = thresholdTempIn;
        this.thresholdTempOut = thresholdTempOut;
        this.thresholdWaterFlow = thresholdWaterFlow;
    }

    // Getters – Jackson uses these to serialize
    public int getThresholdTempIn() {
        return thresholdTempIn;
    }

    public int getThresholdTempOut() {
        return thresholdTempOut;
    }

    public int getThresholdWaterFlow() {
        return thresholdWaterFlow;
    }
}
