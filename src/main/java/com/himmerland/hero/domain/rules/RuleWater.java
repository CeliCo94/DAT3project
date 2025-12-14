package com.himmerland.hero.domain.rules;

import com.himmerland.hero.domain.measurements.Measurement;

public class RuleWater extends Rule {

    private Integer thresholdWaterFlow;

    public RuleWater() {
        super();
        setConsumptionType("VAND");
    }

    public Integer getThresholdWaterFlow() {
        return thresholdWaterFlow;
    }

    public void setThresholdWaterFlow(Integer thresholdWaterFlow) {
        this.thresholdWaterFlow = thresholdWaterFlow;
    }

    @Override
    public boolean isBroken(Measurement measurement){

        if(
            this.thresholdWaterFlow  < measurement.getFlow()
        ) 
            {return true;}
        else 
            {return false;}
    }
}