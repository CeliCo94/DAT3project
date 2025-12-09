package com.himmerland.hero.service.repositories;

import com.himmerland.hero.domain.meters.Meter;
import java.nio.file.Path;
import java.util.List;

@Repository
public class MeterRepository extends BaseRepository<Meter> {

    public MeterRepository(Path dataDir) {
        super(dataDir, "meters", Meter.class);
    }

    public List<Meter> FilterForMeterNumber(String MeterNumber) {
        List<Meter> MeterList = findAll().stream()
                .filter(m -> m.getMeterNumber().equals(MeterNumber))
                .toList();

        return MeterList;
    }

    @Override
    protected Class<Meter> getEntityClass() {
        return Meter.class;
    }
}