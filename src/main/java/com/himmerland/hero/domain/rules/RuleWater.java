package com.himmerland.hero.domain.rules;

public class RuleWater extends Rule {

    private Integer thresholdWaterFlow;

    public RuleWater() {
        super();
        setConsumptionType("WATER");
    }

    public Integer getThresholdWaterFlow() {
        return thresholdWaterFlow;
    }

    public void setThresholdWaterFlow(Integer thresholdWaterFlow) {
        this.thresholdWaterFlow = thresholdWaterFlow;
    }
}
