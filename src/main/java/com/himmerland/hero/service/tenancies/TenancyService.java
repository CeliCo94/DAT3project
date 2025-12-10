package com.himmerland.hero.service.tenancies;

import com.himmerland.hero.domain.tenancies.Tenancy;
import com.himmerland.hero.service.repositories.TenancyRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TenancyService {
    private final TenancyRepository repository;

    public TenancyService(TenancyRepository tenancyRepo) {
        this.repository = tenancyRepo;
    }

    public List<TenancyDTO> findAll() {
        return repository.findAll().stream()
            .map(TenancyDTO::fromDomain)
            .collect(Collectors.toList());
    }

    public TenancyDTO getTenancy(String id) {
        validateId(id);
        return repository.findById(id)
            .map(TenancyDTO::fromDomain)
            .orElseThrow(() -> new IllegalArgumentException("Tenancy not found: " + id));
    }

    public TenancyDTO createTenancy(TenancyDTO dto) {
        Objects.requireNonNull(dto, "Create payload cannot be null");

        // Check if tenancy already exists
        if (dto.id() != null && !dto.id().isBlank()) {
            repository.findById(dto.id()).ifPresent(existing -> {
                throw new IllegalArgumentException("Tenancy already exists: " + dto.id());
            });
        }

        // Convert DTO to domain object
        Tenancy tenancy = dto.toDomain();
        
        // Save and convert back to DTO
        Tenancy saved = repository.save(tenancy);
        return TenancyDTO.fromDomain(saved);
    }

    public TenancyDTO updateTenancy(TenancyDTO dto) {
        Objects.requireNonNull(dto, "Update payload cannot be null");
        validateId(dto.id());

        Tenancy existing = repository.findById(dto.id())
            .orElseThrow(() -> new IllegalArgumentException("Tenancy not found: " + dto.id()));

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

        Tenancy updated = repository.save(existing);
        return TenancyDTO.fromDomain(updated);
    }

    public void deleteTenancy(String id) {
        validateId(id);
        repository.deleteById(id);
    }

    private void validateId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id cannot be null or blank");
        }
    }
}