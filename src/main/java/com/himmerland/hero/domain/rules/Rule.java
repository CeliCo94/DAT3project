package com.himmerland.hero.domain.rules;

import com.himmerland.hero.service.helperclasses.id.IdentifiableBase;

public abstract class Rule extends IdentifiableBase {
    private String name = "";
    private String description = "";
    private String consumptionType;
    private int duration;

    protected Rule() {}

    protected Rule(String name, String description, String consumptionType, int duration){
        this.name = name;
        this.description = description;
        this.consumptionType = consumptionType;   // FIXED
        this.duration = duration;
    }

    public String getConsumptionType() { 
        return consumptionType; 
    }

    public void setConsumptionType(String consumptionType) {
        this.consumptionType = consumptionType;   // FIXED (semicolon)
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {       // Add setter so backend can set value
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {            // Add setter
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {  // Add setter
        this.description = description;
    }
}