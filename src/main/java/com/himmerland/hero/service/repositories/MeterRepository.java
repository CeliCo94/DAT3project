package com.himmerland.hero.service.repositories;

import com.himmerland.hero.domain.meters.Meter;
import java.nio.file.Path;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class MeterRepository extends BaseRepository<Meter> {

    public MeterRepository(Path dataDir) {
        super(dataDir, "meters", Meter.class);
    }

    public Optional<Meter> findMeterByNumber(String meterNumber) {
    return findAll().stream()
        .filter(meter -> meterNumber != null && meterNumber.equals(meter.getMeterNumber()))
        .findFirst();
}

    @Override
    protected Class<Meter> getEntityClass() {
        return Meter.class;
    }
}