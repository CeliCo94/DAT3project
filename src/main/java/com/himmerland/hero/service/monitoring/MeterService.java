package com.himmerland.hero.service.monitoring;

import com.himmerland.hero.domain.meters.Meter;
import com.himmerland.hero.service.measurements.MeasurementCSVImporter.dto.MeasurementDTO;
import com.himmerland.hero.service.repositories.MeterRepository;

import java.util.List;
import java.util.Optional;

public class MeterService {

    private final MeterRepository meterRepository;

    public MeterService(MeterRepository meterRepository) {
        this.meterRepository = meterRepository;
    }

    public void ensureMeterExists(MeasurementDTO dto) {

        String meterNumber = dto.getmeterNumber();

        Optional<Meter> databaseMeter = meterRepository.findMeterByNumber(meterNumber);

        if (databaseMeter.isPresent()) {
            System.out.println(" Meter already exists: " + meterNumber);

            return;
        } else {
            Meter meter = new Meter(
            dto.getmeterNumber(),
            dto.getconsumptionType(),
            dto.getAddress());

            meterRepository.save(meter);
            System.out.println(" Created new meter: " + meterNumber);
            return;
        }

    }
}