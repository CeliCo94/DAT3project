package com.himmerland.hero.domain.rules;

import com.himmerland.hero.service.helperclasses.id.IdentifiableBase;

public abstract class Rule extends IdentifiableBase {
    private String name = "";
    private String description = "";
    private String consumptionType;
    private int duration;

    public Rule() {}

    public Rule(String name, String description, String consumptionsType, int duration){
        this.name = name;
        this.description = description;
        this.consumptionType = consumptionType;
        this.duration = duration;
    }

    public String getConsumptionType(){
        return this.consumptionType;
    }

    public int getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    
}
