package com.himmerland.hero.domain.rules;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.service.helperclasses.id.IdentifiableBase;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RuleHeat.class, name = "ruleHeat"),
        @JsonSubTypes.Type(value = RuleWater.class, name = "ruleWater"),
        @JsonSubTypes.Type(value = RuleHumidity.class, name = "ruleHumidity")
})
public abstract class Rule extends IdentifiableBase {
    private String name = "";
    private String description = "";
    private String consumptionType;
    private int duration;
    private boolean active;

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public abstract boolean isBroken(Measurement measurement);
}
