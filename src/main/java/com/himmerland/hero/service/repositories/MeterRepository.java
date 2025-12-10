package com.himmerland.hero.repositories;

import com.himmerland.hero.domain.meters.Meter;
import java.nio.file.Path;
import java.util.List;

import org.springframework.stereotype.Repository;
import java.util.Optional;

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