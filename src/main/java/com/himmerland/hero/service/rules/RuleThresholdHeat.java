package com.himmerland.hero.service.rules;

public class RuleThresholdHeat implements IRule {

    private String name = "";
    private String description = "";

    private int thresholdTempIn;
    private int thresholdTempOut;
    private int thresholdWaterFlow;
    private int duration;

    public RuleThresholdHeat() {} // <-- Jackson needs a no-arg constructor

    public RuleThresholdHeat(String name, int thresholdTempIn, int thresholdTempOut, int thresholdWaterFlow, int duration) {
        this.name = name;
        this.thresholdTempIn = thresholdTempIn;
        this.thresholdTempOut = thresholdTempOut;
        this.thresholdWaterFlow = thresholdWaterFlow;
        this.duration = duration;
    }

    // Getters – Jackson uses these to serialize
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getThresholdTempIn() {
        return thresholdTempIn;
    }

    public int getThresholdTempOut() {
        return thresholdTempOut;
    }

    public int getThresholdWaterFlow() {
        return thresholdWaterFlow;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public int testRule() {
        return 0;
    }

    @Override
    public void activateRule() {}

    @Override
    public void applyDescription(String description) {
        this.description = description;
    }
}
