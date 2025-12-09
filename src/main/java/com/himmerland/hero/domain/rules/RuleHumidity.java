package com.himmerland.hero.domain.rules;

import com.himmerland.hero.domain.measurements.Measurement;

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

    @Override
    public boolean isBroken(Measurement measurement){

        if(
            this.thresholdHumidity  < measurement.getHumidity()
        ) 
            {return true;}
        else 
            {return false;}
    }
}