package com.himmerland.hero.service.departments;

import com.himmerland.hero.domain.departments.Department;
import com.himmerland.hero.service.repositories.DepartmentRepository;

import jakarta.annotation.PostConstruct;

import com.himmerland.hero.service.importer.DepCSVImporter;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service 
public class DepartmentService {

    private final DepartmentRepository repository;
    private static final Pattern VALID_ID = Pattern.compile("^[A-Za-z0-9_-]{3,32}$");
    private DepCSVImporter importer;

    public DepartmentService(DepartmentRepository depRepo, DepCSVImporter importer) {
        this.repository = depRepo;
        this.importer = importer;
    }

    public List<Department> findAll() {
        return repository.findAll();
    }

    @PostConstruct
    public void start() {
        ReadDepartmentData("src\\main\\resources\\csvDep\\departments(Ark1).csv");
    }

    public void deleteAllObjects() {
        List<Department> all = repository.findAll();
        for (Department d : all) {
            repository.deleteById(d.getId());
        }
    }

    public boolean CreateAndSaveDepartment(DepartmentDTO dep) {
        try {
            if (checkIfDepartmentExists(dep)) {
                return false;
            } else {
            Department d = CreateNewDepartment(dep);

            repository.save(d);

            return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Department CreateNewDepartment(DepartmentDTO dep) {
        Department d = new Department(dep.name(), dep.email());
        return d;
    }

    public void ReadDepartmentData(String filepath) {
        List<DepartmentDTO> csvData = importer.readCSVFileToDepartmentDTOs(filepath);

        for (DepartmentDTO dto : csvData) {
            CreateAndSaveDepartment(dto);
        }
    }

    public Boolean checkIfDepartmentExists(DepartmentDTO dto) {
        try {
            Department d = GetDepartmentFromName(dto.name());
            return true; // found
        } catch (RuntimeException e) {
            return false; // not found
        }
    }

    public Department GetDepartmentFromName(String name) {
        return repository.getFromName(name)
            .orElseThrow(() -> new RuntimeException("Tenancy not found for address: " + name));
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public Department getDepartment(String id) {
        validateId(id);
        return repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Department not found: " + id));
    }

    public Department editDepartment(String id, DepartmentDTO payload) {
        Department existing = getDepartment(id);

        if (payload.name() != null && !payload.name().isBlank()) {
            existing.setName(payload.name());
        }

        if (payload.email() != null && !payload.email().isBlank()) {
            existing.setEmail(payload.email());
        }
        return repository.save(existing);
    }

    public void validateId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id cannot be null or blank");
        }
        if (!VALID_ID.matcher(id).matches()) {
            throw new IllegalArgumentException("Invalid id format: " + id);
        }
    }
}