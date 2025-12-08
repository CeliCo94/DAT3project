package com.himmerland.hero.repositories;

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
