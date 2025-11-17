package com.himmerland.hero.domain.rules;

import com.himmerland.hero.service.helperclasses.id.IdentifiableBase;

public abstract class Rule extends IdentifiableBase {
    private String consumptionType;
    
    public void compareMeterToThresholds(){

    }

    public void setConsumptionType(String consumptionsType){
        this.consumptionType = consumptionType;
    }

    public String getConsumptionType(){
        return this.consumptionType;
    }
}
