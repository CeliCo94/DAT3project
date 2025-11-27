package com.himmerland.hero.domain.rules;

import com.himmerland.hero.service.helperclasses.id.IdentifiableBase;
import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.domain.measurements.MeasurementHeat;
import com.himmerland.hero.domain.notifications.Notification;
import com.himmerland.hero.service.helperclasses.enums.Criticality;
import java.util.List;

public class RuleThresholdHeat extends Rule {

    private int thresholdTempIn;
    private int thresholdTempOut;
    private int thresholdWaterFlow;

    public RuleThresholdHeat() {
        
    } // <-- Jackson needs a no-arg constructor

    public RuleThresholdHeat(String name, String description, String consumptionType, int duration, int thresholdTempIn, int thresholdTempOut, int thresholdWaterFlow) {
        super(name, description, consumptionType, duration);
        this.thresholdTempIn = thresholdTempIn;
        this.thresholdTempOut = thresholdTempOut;
        this.thresholdWaterFlow = thresholdWaterFlow;
    }

    // Getters – Jackson uses these to serialize
    public int getThresholdTempIn() {
        return thresholdTempIn;
    }

    public int getThresholdTempOut() {
        return thresholdTempOut;
    }

    public int getThresholdWaterFlow() {
        return thresholdWaterFlow;
    }

    public boolean isBroken(Measurement m) {
        if (m instanceof MeasurementHeat) {
            MeasurementHeat heatMeasurement = (MeasurementHeat) m;
            return heatMeasurement.getForwardTemperature() >= this.thresholdTempIn
            && heatMeasurement.getReturnTemperature() >= this.thresholdTempOut
            && heatMeasurement.getVolume() >= this.thresholdWaterFlow;
        }
        return false;
    }

    public Notification buildNotification(Measurement m) {
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
            this.getName(), // rule name
            Criticality.Low, // criticality
            heatMeasurement.getTimestamp(), // timeStamp
            true, // isActive
            false // isSent
        );
    }
}
