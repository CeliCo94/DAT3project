package com.himmerland.hero.repositories;

import com.himmerland.hero.domain.tenancies.Tenancy;
import com.himmerland.hero.service.helperclasses.handlejson.ReadAllJsonToList;
import com.himmerland.hero.service.io.JsonStorage;
import com.himmerland.hero.service.io.StorageStrategy;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;


public class TenancyRepository {
    
    private final StorageStrategy<Tenancy> storage;
    private final Path tenanciesDir;

    public TenancyRepository(Path dataDir) {
        this.tenanciesDir = dataDir.resolve("tenancies");
        this.storage = new JsonStorage<>(dataDir, "tenancies", Tenancy.class);
    }

    public Optional<Tenancy> findById(String id) {
        return storage.read(id);
    }

    public Tenancy save(Tenancy tenancy) {
        storage.write(tenancy);
        return tenancy;
    }

    public List<Tenancy> findAll() {
        return ReadAllJsonToList.readAll(tenanciesDir, Tenancy.class);
    }

    public boolean existsById(String id) {
        return storage.read(id).isPresent();
    }

    public void deleteById(String id) {
        storage.delete(id);
    }
}
