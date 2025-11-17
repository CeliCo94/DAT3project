package com.himmerland.hero.domain.rules;

import com.himmerland.hero.domain.rules.Rule;

public class ruleThresholdHeat extends Rule {

    private String name = "";
    private String description = "";

    private int thresholdTempIn;
    private int thresholdTempOut;
    private int thresholdWaterFlow;
    private int duration;

    public ruleThresholdHeat() {} // <-- Jackson needs a no-arg constructor

    public ruleThresholdHeat(String name, int thresholdTempIn, int thresholdTempOut, int thresholdWaterFlow, int duration) {
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
}
