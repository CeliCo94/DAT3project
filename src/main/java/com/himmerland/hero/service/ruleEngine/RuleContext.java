package com.himmerland.hero.service.ruleEngine;

import com.himmerland.hero.domain.meters.Meter;
import com.himmerland.hero.service.repositories.MeterRepository;

public class RuleContext {

    private final MeterRepository meterRepository;

    public RuleContext(MeterRepository meterRepository) {
        this.meterRepository = meterRepository;
    }

    public Meter getMeter(String meterNumber) {
    return meterRepository.findMeterByNumber(meterNumber)
            .orElseThrow(() -> new IllegalArgumentException("Meter not found: " + meterNumber));
    }

    public String getConsumptionType(String meterNumber) {
        return getMeter(meterNumber).getConsumptionType();
    }
}
