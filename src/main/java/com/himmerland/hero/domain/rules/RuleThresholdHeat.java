package com.himmerland.hero.domain.rules;

import com.himmerland.hero.service.helperclasses.id.IdentifiableBase;
import com.himmerland.hero.service.helperclasses.enums.Criticality;

public class RuleThresholdHeat extends IdentifiableBase implements IRule {

    // Basic info
    private String name = "";
    private String description = "";
    private Criticality criticality = Criticality.Low;

    // Seasonal settings
    private boolean seasonal = false;
    private String startDate; // optional
    private String endDate;   // optional

    // Thresholds
    private int thresholdTempIn;
    private int thresholdTempOut;
    private int thresholdWaterFlow;
    private int duration;

    public RuleThresholdHeat() {} // <-- Jackson needs a no-arg constructor

    // --- Getters and setters --
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Criticality getCriticality() {
        return criticality;
    }
    public void setCriticality(Criticality criticality) {
        this.criticality = criticality;
    }

    public boolean isSeasonal() {
        return seasonal;
    }
    public void setSeasonal(boolean seasonal) {
        this.seasonal = seasonal;
    }

    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getThresholdTempIn() {
        return thresholdTempIn;
    }
    public void setThresholdTempIn(int thresholdTempIn) {
        this.thresholdTempIn = thresholdTempIn;
    }

    public int getThresholdTempOut() {
        return thresholdTempOut;
    }
    public void setThresholdTempOut(int thresholdTempOut) {
        this.thresholdTempOut = thresholdTempOut;
    }

    public int getThresholdWaterFlow() {
        return thresholdWaterFlow;
    }
    public void setThresholdWaterFlow(int thresholdWaterFlow) {
        this.thresholdWaterFlow = thresholdWaterFlow;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public int testRule() {
        return 0;
    }

    @Override
    public void activateRule() {}

    @Override
    public void applyDescription(String description) {
        this.description = description;
    }
}