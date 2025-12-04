package com.himmerland.hero.repositories;

import com.himmerland.hero.domain.tenancies.Tenancy;
import java.nio.file.Path;



public class TenancyRepository extends BaseRepository<Tenancy> {

    public TenancyRepository(Path dataDir) {
        super(dataDir, "tenancies", Tenancy.class);
    }
    
    @Override
    protected Class<Tenancy> getEntityClass() {
        return Tenancy.class;
    }
}
