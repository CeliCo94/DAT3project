package com.himmerland.hero.service.repositories;

import com.himmerland.hero.domain.tenancies.Tenancy;

import java.nio.file.Path;

import org.springframework.stereotype.Repository;

@Repository
public class TenancyRepository extends BaseRepository<Tenancy> {

    public TenancyRepository(Path dataDir) {
        super(dataDir, "tenancies", Tenancy.class);
    }
    
    @Override
    protected Class<Tenancy> getEntityClass() {
        return Tenancy.class;
    }
}
