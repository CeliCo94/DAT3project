package com.himmerland.hero.domain.meters;

import com.himmerland.hero.service.helperclasses.id.IdentifiableBase;

public class Meter extends IdentifiableBase {
    private String meterNumber;
    private String meterType;
    private String consumptionType;
    private String timestamp;
    private int infocode = 0;

    public Meter() {}

    public Meter(String meterNumber, String meterType, String consumptionType, String timestamp, int infocode) {
        this.meterNumber = meterNumber;
        this.meterType = meterType;
        this.consumptionType = consumptionType;
        this.timestamp = timestamp;
        this.infocode = infocode;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public void setMeterNumber(String meterNumber) {
        this.meterNumber = meterNumber;
    }

    public String getMeterType() {
        return meterType;
    }

    public void setMeterType(String meterType) {
        this.meterType = meterType;
    }

    public String getConsumptionType() {
        return consumptionType;
    }

    public void setConsumptionType(String consumptionType) {
        this.consumptionType = consumptionType;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getInfocode() {
        return infocode;
    }

    public void setInfocode(int infocode) {
        this.infocode = infocode;
    }
}
