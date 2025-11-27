package com.himmerland.hero.domain.rules;

import com.himmerland.hero.service.helperclasses.id.IdentifiableBase;
import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.domain.notifications.Notification;

public abstract class Rule extends IdentifiableBase {
    private String name = "";
    private String description = "";
    private String consumptionType;
    private int duration;

    public Rule() {}

    public Rule(String name, String description, String consumptionType, int duration){
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

    public abstract boolean isBroken(Measurement m);

    public abstract Notification buildNotification(Measurement m);
    
}
