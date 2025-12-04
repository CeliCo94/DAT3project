package com.himmerland.hero.domain.rules;

public class RuleHumidity extends Rule {

    private Integer thresholdHumidity;

    public RuleHumidity() {
        super();
        setConsumptionType("HUMIDITY");
    }

    public Integer getThresholdHumidity() {
        return thresholdHumidity;
    }

    public void setThresholdHumidity(Integer thresholdHumidity) {
        this.thresholdHumidity = thresholdHumidity;
    }
}