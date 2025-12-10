package com.himmerland.hero.domain.rules;

import com.himmerland.hero.domain.measurements.Measurement;

public class RuleHeat extends Rule {

    private Integer thresholdTempIn;
    private Integer thresholdTempOut;
    private Integer thresholdHeatWaterFlow;

    public RuleHeat() {
        super();
        setConsumptionType("HEAT");
    }

    public Integer getThresholdTempIn() {
        return thresholdTempIn;
    }

    public void setThresholdTempIn(Integer thresholdTempIn) {
        this.thresholdTempIn = thresholdTempIn;
    }

    public Integer getThresholdTempOut() {
        return thresholdTempOut;
    }

    public void setThresholdTempOut(Integer thresholdTempOut) {
        this.thresholdTempOut = thresholdTempOut;
    }

    public Integer getThresholdHeatWaterFlow() {
        return thresholdHeatWaterFlow;
    }

    public void setThresholdHeatWaterFlow(Integer thresholdHeatWaterFlow) {
        this.thresholdHeatWaterFlow = thresholdHeatWaterFlow;
    }

    @Override
    public boolean isBroken(Measurement measurement){

        if(
            this.thresholdTempIn         < measurement.getForwardTemperature() &&
            this.thresholdTempOut        < measurement.getReturnTemperature() &&
            this.thresholdHeatWaterFlow  < measurement.getFlow()
        ) 
            {return true;}
        else 
            {return false;}

    }
}