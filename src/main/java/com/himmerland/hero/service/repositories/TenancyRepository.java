package com.himmerland.hero.service.repositories;

import com.himmerland.hero.domain.departments.Department;
import com.himmerland.hero.domain.tenancies.Tenancy;

import java.nio.file.Path;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TenancyRepository extends BaseRepository<Tenancy> {

    public TenancyRepository(Path dataDir) {
        super(dataDir, "tenancies", Tenancy.class);
    }

    public Optional<Tenancy> getFromAddress(String address) {
        List<Tenancy> all = findAll();
    System.out.println("Total tenancies: " + all.size());
    all.forEach(d -> System.out.println("Tenancy: '" + d.getAddress() + "'"));
        return findAll().stream()
                .filter(t -> t.getAddress().equals(address))
                .findFirst();
    }
    
    @Override
    protected Class<Tenancy> getEntityClass() {
        return Tenancy.class;
    }
}
