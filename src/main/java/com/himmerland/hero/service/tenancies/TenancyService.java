package com.himmerland.hero.service.tenancies;

import com.himmerland.hero.domain.departments.Department;
import com.himmerland.hero.domain.tenancies.Tenancy;
import com.himmerland.hero.service.departments.DepartmentDTO;
import com.himmerland.hero.service.measurements.MeasurementCSVImporter.dto.MeasurementDTO;
import com.himmerland.hero.service.repositories.TenancyRepository;
import com.himmerland.hero.service.importer.TenancyCSVImporter;

import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

@Service
public class TenancyService {
    private final TenancyRepository repository;
    private TenancyCSVImporter importer;

    public TenancyService(TenancyRepository tenancyRepo, TenancyCSVImporter importer) {
        this.repository = tenancyRepo;
        this.importer = importer;
    }

    public boolean CreateAndSaveTenancy(TenancyDTO tenancy) {
        try {
            if (checkIfTenancyExists(tenancy)) {
                return false;
            } else {
                Tenancy t = CreateNewTenancy(tenancy);

                repository.save(t);

                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getDepartmentFromAddress(String address) {
        return repository.findAll().stream()
            .filter(n -> address.equals(n.getAddress()))
            .map(n -> n.getDepartmentName())
            .findFirst()
            .orElse(null);
    }

    private Tenancy CreateNewTenancy(TenancyDTO tenancy) {
        Tenancy t = new Tenancy(tenancy.getdepartmentName(), tenancy.getAddress(), tenancy.getcity(),
                                tenancy.getpostalCode());
        return t;
    }

    public Tenancy GetTenancyFromAddress(String address) {
        return repository.getFromAddress(address)
            .orElseThrow(() -> new RuntimeException("Tenancy not found for address: " + address));
    }

    public void ReadTenancyData(String filepath) {
        List<TenancyDTO> csvData = importer.readCSVFileToTenancyDTOs(filepath);

        for (TenancyDTO dto : csvData) {
            CreateAndSaveTenancy(dto);
        }
    }

    public TenancyDTO getTenancy(String id) {
        validateId(id);
        return repository.findById(id)
            .map(TenancyDTO::fromDomain)
            .orElseThrow(() -> new IllegalArgumentException("Tenancy not found: " + id));
    }

    public List<TenancyDTO> findAll() {
        List<Tenancy> tenancies = repository.findAll();
        List<TenancyDTO> dtos = new ArrayList();
        for (Tenancy t : tenancies) {
            System.out.println(t.getAddress());
            dtos.add(new TenancyDTO(t.getDepartmentName(), t.getAddress(), t.getCity(), t.getPostalCode()));
        }
        return dtos;
    }

    public Boolean checkIfTenancyExists(TenancyDTO dto) {
        try {
            GetTenancyFromAddress(dto.getAddress());
            return true; // found
        } catch (RuntimeException e) {
            return false; // not found
        }
    }
/*
    public TenancyDTO updateTenancy(TenancyDTO dto) {
        
        Objects.requireNonNull(dto, "Update payload cannot be null");
        //validateId(dto.id());

        
        Tenancy existing = repository.findById(dto.id())
            .orElseThrow(() -> new IllegalArgumentException("Tenancy not found: " + dto.id()));

        if (dto.meterNumber() != null && !dto.meterNumber().isBlank()) {
            existing.setMeterNumber(dto.meterNumber());
        }
        
        if (dto.departmentName() != null && !dto.departmentName().isBlank()) {
            existing.setDepartmentName(dto.departmentName());
        }
        
        if (dto.tenancyNumber() != null && !dto.tenancyNumber().isBlank()) {
            existing.setTenancyNumber(dto.tenancyNumber());
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
        
        //Tenancy updated = repository.save(existing);
        return dto; //TenancyDTO.fromDomain(updated);
        
    }
    */

    public void deleteTenancy(String id) {
        validateId(id);
        repository.deleteById(id);
    }

    private void validateId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id cannot be null or blank");
        }
    }

    
        
        // If departmentName is provided, try to find the department by name
    private String resolveDepartmentId(TenancyDTO dto) {
        if (dto.departmentName() != null && !dto.departmentName().isBlank()) {
            return departmentService.findAll().stream()
                .filter(dept -> dept.getName() != null && dept.getName().equals(dto.departmentName()))
                .findFirst()
                .map(dept -> dept.getId())
                .orElse(dto.departmentName()); // Fallback: assume it's actually an ID if not found
        }
        
        return null;
    }
}