package com.himmerland.hero.web;

import com.himmerland.hero.service.helperclasses.enums.Criticality;

public class RuleRequest {

    private String type;
    private String name;
    private String description;
    private Criticality criticality;
    private Integer duration;

    private Integer thresholdTempIn;
    private Integer thresholdTempOut;
    private Integer thresholdHeatWaterFlow;
    private Integer thresholdWaterFlow;
    private Integer thresholdHumidity;

    // GETTERS AND SETTERS
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Criticality getCriticality(){ return criticality; }
    public void setCriticality(Criticality criticality) { this.criticality = criticality; }

    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }

    public Integer getThresholdTempIn() { return thresholdTempIn; }
    public void setThresholdTempIn(Integer thresholdTempIn) { this.thresholdTempIn = thresholdTempIn; }

    public Integer getThresholdTempOut() { return thresholdTempOut; }
    public void setThresholdTempOut(Integer thresholdTempOut) { this.thresholdTempOut = thresholdTempOut; }

    public Integer getThresholdHeatWaterFlow() { return thresholdHeatWaterFlow; }
    public void setThresholdHeatWaterFlow(Integer thresholdHeatWaterFlow) { this.thresholdHeatWaterFlow = thresholdHeatWaterFlow; }

    public Integer getThresholdWaterFlow() { return thresholdWaterFlow; }
    public void setThresholdWaterFlow(Integer thresholdWaterFlow) { this.thresholdWaterFlow = thresholdWaterFlow; }

    public Integer getThresholdHumidity() { return thresholdHumidity; }
    public void setThresholdHumidity(Integer thresholdHumidity) { this.thresholdHumidity = thresholdHumidity; }
}
