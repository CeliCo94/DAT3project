package com.himmerland.hero.service.monitoring;

import com.himmerland.hero.domain.meters.Meter;
import com.himmerland.hero.service.repositories.MeterRepository;
import com.himmerland.hero.service.Measurements.MeasurementCSVImporter.dto.MeasurementDTO;

import java.util.List;

public class MeterService {

    private final MeterRepository meterRepository;

    public MeterService(MeterRepository meterRepository) {
        this.meterRepository = meterRepository;
    }

    public void ensureMeterExists(MeasurementDTO dto) {

        String meterNumber = dto.getmeterNumber();

        List<Meter> meterlist = meterRepository.FilterForMeterNumber(meterNumber);

        if (meterlist.size() == 0) {

            Meter meter = new Meter(
            dto.getmeterNumber(),
            dto.getconsumptionType(),
            dto.getAddress());

            meterRepository.save(meter);
            System.out.println(" Created new meter: " + meterNumber);
            return;
        } else {
            System.out.println(" Meter already exists: " + meterNumber);

            return;
        }
    }
}