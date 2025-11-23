package com.himmerland.hero.domain.rules;

import com.himmerland.hero.service.helperclasses.id.IdentifiableBase;
import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.domain.measurements.MeasurementHeat;
import com.himmerland.hero.domain.notifications.Notification;
import com.himmerland.hero.service.helperclasses.enums.Criticality;
import java.util.List;

public class RuleThresholdHeat extends IdentifiableBase implements IRule {

    private String name = "";
    private String description = "";

    private int thresholdTempIn;
    private int thresholdTempOut;
    private int thresholdWaterFlow;
    private int duration;

    public RuleThresholdHeat() {} // <-- Jackson needs a no-arg constructor

    public RuleThresholdHeat(String name, int thresholdTempIn, int thresholdTempOut, int thresholdWaterFlow, int duration) {
        this.name = name;
        this.thresholdTempIn = thresholdTempIn;
        this.thresholdTempOut = thresholdTempOut;
        this.thresholdWaterFlow = thresholdWaterFlow;
        this.duration = duration;
    }

    // Getters – Jackson uses these to serialize
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getThresholdTempIn() {
        return thresholdTempIn;
    }

    public int getThresholdTempOut() {
        return thresholdTempOut;
    }

    public int getThresholdWaterFlow() {
        return thresholdWaterFlow;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public int testRule(List<Measurement> measurements) {
        if (measurements == null || measurements.isEmpty()) {
            return 0;
        }

        int counter = 0;

        for (Measurement measurement : measurements) {
            // Cast to MeasurementHeat since this rule works with heat measurements
            if (measurement instanceof MeasurementHeat) {
                MeasurementHeat heatMeasurement = (MeasurementHeat) measurement;
                // Check if the measurement meets all threshold criteria
                if (heatMeasurement.getForwardTemperature() >= this.thresholdTempIn
                && heatMeasurement.getReturnTemperature() >= this.thresholdTempOut
                && heatMeasurement.getVolume() >= this.thresholdWaterFlow) {
                    counter++;
                }
            }
        }
        // Return the count of measurements that triggered the rule
        // The rule should activate if counter is >=duration
        return counter;
    }

    @Override
    public void activateRule() {}

    @Override
    public void applyDescription(String description) {
        this.description = description;
    }
    
    @Override
    public boolean isBrokenBy(Measurement m, RuleContext ctx) {
        if (m instanceof MeasurementHeat){
            MeasurementHeat heatMeasurement = (MeasurementHeat)m;
            return heatMeasurement.getForwardTemperature() >= this.thresholdTempIn
            && heatMeasurement.getReturnTemperature() >= this.thresholdTempOut
            && heatMeasurement.getVolume() >= this.thresholdWaterFlow;
        }
        return false;
    }

    @Override
    public Notification buildNotification(Measurement m, RuleContext ctx) {
        if (!(m instanceof MeasurementHeat)) {
            return null;
        }
        MeasurementHeat heatMeasurement = (MeasurementHeat)m;

        // Build a cause message describing what thresholds were exceeded
        StringBuilder cause = new StringBuilder("Heat threshold exceeded: ");
        if (heatMeasurement.getForwardTemperature() >= this.thresholdTempIn) {
            cause.append("Forward temp (").append(heatMeasurement.getForwardTemperature())
            .append(") >= ").append(this.thresholdTempIn).append("; ");
        }
        if (heatMeasurement.getReturnTemperature() >= this.thresholdTempOut) {
            cause.append("Return temp (").append(heatMeasurement.getReturnTemperature())
            .append(") >= ").append(this.thresholdTempOut).append("; ");
        }
        if (heatMeasurement.getVolume() >= this.thresholdWaterFlow) {
            cause.append("Volume (").append(heatMeasurement.getVolume())
            .append(") >= ").append(this.thresholdWaterFlow).append("; ");
        }

        return new Notification(
            heatMeasurement.getMeterNumber(), //address
            cause.toString(), // cause
            this.name, // rule name
            Criticality.Low, // criticality
            heatMeasurement.getTimestamp(), // timeStamp
            true, // isActive
            false // isSent
        );
    }
}
