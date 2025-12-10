package com.himmerland.hero.service.monitoring;

import com.himmerland.hero.domain.meters.Meter;
import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.service.repositories.MeterRepository;

public class MeterService {

    private final MeterRepository meterRepository;

    public MeterService(MeterRepository meterRepository) {
        this.meterRepository = meterRepository;
    }

    public void ensureMeterExists(Measurement measurement) {

        String meterNumber = measurement.getMeterNumber();

        if (meterNumber == null || meterNumber.isBlank()) {
            System.out.println("Missing meter number, measurement is invalid");
            return;
        }

        if (meterRepository.existsById(meterNumber)) {
            return;
        }

        Meter meter = new Meter(
                measurement.getMeterNumber(),
                measurement.getMeterType(),
                measurement.getConsumptionType(),
                measurement.getTimestamp(),
                measurement.getInfoCode(),
                null); // address is not used in this context   

        meterRepository.save(meter);

        System.out.println(" Created new meter: " + meterNumber);
    }
}