package com.himmerland.hero.service.tenancies;

import com.himmerland.hero.domain.tenancies.Tenancy;
import com.himmerland.hero.service.io.JsonStorage;
import com.himmerland.hero.service.io.StorageStrategy;

import java.nio.file.Path;
import java.util.Objects;

public class TenancyService {
    private final StorageStrategy<Tenancy> storage;

    public TenancyService(Path dataDir) {
        this.storage = new JsonStorage<>(dataDir, "tenancies", Tenancy.class);
    }

    public Tenancy getTenancy(String id) {
        validateId(id);
        return storage.read(id)
            .orElseThrow(() -> new IllegalArgumentException("Tenancy not found: " + id));
    }

    public Tenancy updateTenancy(TenancyDTO dto) {
        Objects.requireNonNull(dto, "Edit payload cannot be null");
        validateId(dto.id());

        Tenancy existing = getTenancy(dto.id());

        if (dto.meterNumber() != null && !dto.meterNumber().isBlank()) {
            existing.setMeterNumber(dto.meterNumber());
        }

        if (dto.departmentId() != null && !dto.departmentId().isBlank()) {
            existing.setDepartmentId(dto.departmentId());
        }

        if (dto.tennancyNumber() != null && !dto.tennancyNumber().isBlank()) {
            existing.setTennancyNumber(dto.tennancyNumber());
        }
        
        if (dto.address() != null && !dto.address().isBlank()) {
            existing.setAddress(dto.address());
        }

        if (dto.city() != null && !dto.city().isBlank()) {
            existing.setCity(dto.city());
        }
        
        if (dto.postalCode() != null && !dto.postalCode().isBlank()) {
            existing.setPostalCode(dto.postalCode());
        }

        if (dto.active() != null) {
            existing.setActive(dto.active());
        }
        storage.write(existing);
        return(existing);
    }

    public void deleteTenancy(String id) {
        validateId(id);
        storage.delete(id);
    }

    private void validateId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id cannot be null or blank");
        }
    }
}