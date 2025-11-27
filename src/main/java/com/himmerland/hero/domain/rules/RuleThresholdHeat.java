package com.himmerland.hero.domain.rules;

import com.himmerland.hero.service.helperclasses.id.IdentifiableBase;

public class RuleThresholdHeat extends IdentifiableBase implements IRule {

    private String name = "";
    private String description = "";

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
