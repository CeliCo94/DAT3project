package com.himmerland.hero.service.rules;

public class RuleThresholdHeat implements IRule {

    private String name = "";
    private String description ="";

    int thresholdTempIn, 
        thresholdTempOut, 
        thresholdWaterFlow,
        duration;

    public RuleThresholdHeat(String name, int thresholdTempIn, int thresholdTempOut, int thresholdWaterFlow, int duration) {
        this.name = name;
        this.thresholdTempIn = thresholdTempIn;
        this.thresholdTempOut = thresholdTempOut;
        this.thresholdWaterFlow = thresholdWaterFlow;
        this.duration = duration;
    }

    @Override
    public int testRule() {
        // Implementation of the rule testing logic
        return 0; // Placeholder return value
    }

    @Override
    public void activateRule() {
        // Implementation of the rule activation logic
    }

    @Override
    public void applyDescription(String description) {
        this.description = description;
    }    
}
