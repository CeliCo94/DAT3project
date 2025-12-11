package com.himmerland.hero.service.ruleEngine;

import org.springframework.stereotype.Service;

import com.himmerland.hero.domain.meters.Meter;
import com.himmerland.hero.service.repositories.MeterRepository;
import com.himmerland.hero.service.repositories.TenancyRepository;
import com.himmerland.hero.service.tenancies.TenancyService;

@Service
public class RuleContext {

    private final MeterRepository meterRepository;
    private final TenancyService tenancyService;

    public RuleContext(MeterRepository meterRepository, TenancyService tenancyService) {
        this.meterRepository = meterRepository;
        this.tenancyService = tenancyService;
    }

    public Meter getMeter(String meterNumber) {
    return meterRepository.findMeterByNumber(meterNumber)
            .orElseThrow(() -> new IllegalArgumentException("Meter not found: " + meterNumber));
    }

    public String getConsumptionType(String meterNumber) {
        return getMeter(meterNumber).getConsumptionType();
    }

    public String getDepartment(String address) {
        return tenancyService.getDepartmentFromAddress(address);
    }
}
