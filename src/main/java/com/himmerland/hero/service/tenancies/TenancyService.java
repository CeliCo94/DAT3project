package com.himmerland.hero.service.tenancies;

import com.himmerland.hero.domain.tenancies.Tenancy;
import com.himmerland.hero.service.repositories.TenancyRepository;
import com.himmerland.hero.service.importer.TenancyCSVImporter;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class TenancyService {
    private final TenancyRepository repository;
    private TenancyCSVImporter importer;

    public TenancyService(TenancyRepository tenancyRepo, TenancyCSVImporter importer) {
        this.repository = tenancyRepo;
        this.importer = importer;
    }
    
    public void deleteAllObjects() {
        List<Tenancy> all = repository.findAll();
        for (Tenancy t : all) {
            repository.deleteById(t.getId());
        }
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
            dtos.add(TenancyDTO.fromDomain(t));
        }
        return dtos;
    }

    public Boolean checkIfTenancyExists(TenancyDTO dto) {
        try {
            GetTenancyFromAddress(dto.getAddress());
            return true; 
        } catch (RuntimeException e) {
            return false; 
        }
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