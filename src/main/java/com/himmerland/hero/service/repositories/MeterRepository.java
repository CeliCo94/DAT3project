package com.himmerland.hero.service.repositories;

import java.nio.file.Path;

import com.himmerland.hero.domain.meters.Meter;

public class MeterRepository extends BaseRepository<Meter> {

    public MeterRepository(Path dataDir) {
        super(dataDir, "meters", Meter.class);
    }

    @Override
    protected Class<Meter> getEntityClass() {
        return Meter.class;
    }
}
