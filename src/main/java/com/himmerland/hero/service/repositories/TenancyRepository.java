package com.himmerland.hero.service.repositories;

import com.himmerland.hero.domain.tenancies.Tenancy;

import java.nio.file.Path;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TenancyRepository extends BaseRepository<Tenancy> {

    public TenancyRepository(Path dataDir) {
        super(dataDir, "tenancies", Tenancy.class);
    }

    public Optional<Tenancy> getFromAddress(String address) {
        return findAll().stream()
                .filter(t -> t.getAddress().equals(address))
                .findFirst();
    }
    
    @Override
    protected Class<Tenancy> getEntityClass() {
        return Tenancy.class;
    }
}
