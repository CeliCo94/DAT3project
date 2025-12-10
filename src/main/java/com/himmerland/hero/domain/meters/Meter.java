package com.himmerland.hero.domain.meters;

import com.himmerland.hero.service.helperclasses.id.IdentifiableBase;

public class Meter extends IdentifiableBase {
    private String meterNumber;
    private String consumptionType;
    private String address;

    public Meter() {
        super(); // optional, called automatically
    }

    public Meter(String meterNumber, String consumptionType,
            String address) {
        this.meterNumber = meterNumber;
        this.consumptionType = consumptionType;
        this.address = address;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public void setMeterNumber(String meterNumber) {
        this.meterNumber = meterNumber;
    }

    public String getConsumptionType() {
        return consumptionType;
    }

    public void setConsumptionType(String consumptionType) {
        this.consumptionType = consumptionType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}