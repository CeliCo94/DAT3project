package com.himmerland.hero.service.measurements;

import com.himmerland.hero.service.helperclasses.id.IdentifiableBase;

public class Measurement extends IdentifiableBase {
    private String meterNumber;
    private String meterType;
    private String consumptionType;
    private String timestamp;
    private int infoCode = 0;

    public Measurement(String meterNumber, String meterType, String consumptionType, String timestamp, int infoCode) {
        this.meterNumber = meterNumber;
        this.meterType = meterType;
        this.consumptionType = consumptionType;
        this.timestamp = timestamp;
        this.infoCode = infoCode;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public String getMeterType() {
        return meterType;
    }

    public String getConsumptionType() {
        return consumptionType;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getInfoCode() {
        return infoCode;
    }
}
