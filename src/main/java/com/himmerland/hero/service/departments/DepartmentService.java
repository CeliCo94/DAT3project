package com.himmerland.hero.service.departments;

import com.himmerland.hero.domain.departments.Department;
import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.domain.tenancies.Tenancy;
import com.himmerland.hero.service.measurements.MeasurementFactory;
import com.himmerland.hero.service.measurements.MeasurementCSVImporter.dto.MeasurementDTO;
import com.himmerland.hero.service.repositories.DepartmentRepository;
import com.himmerland.hero.service.tenancies.TenancyDTO;
import com.himmerland.hero.service.importer.DepCSVImporter;

import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
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
            System.out.println(dto.name());
            Department d = GetDepartmentFromName(dto.name());
            System.out.println(d.getName());
            return true; // found
        } catch (RuntimeException e) {
            return false; // not found
        }
    }

    public Department GetDepartmentFromName(String name) {
        return repository.getFromName(name)
            .orElseThrow(() -> new RuntimeException("Tenancy not found for address: " + name));
    }
/*
    public Optional<Department> findById(String id) {
        return repository.findById(id);
    }

    public Department save(Department department) {
        return repository.save(department);
    }
*/
    public void delete(String id) {
        repository.deleteById(id);
    }

    public Department getDepartment(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id cannot be null or blank");
        }
        return repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Department not found: " + id));
    }
/*
    public Department createDepartment(DepartmentDTO payload) {
        validateId(payload.id());
        repository.findById(payload.id()).ifPresent(existing -> {
            throw new IllegalArgumentException("Department already exists: " + payload.id());
        });

        Department department = new Department(payload.id(), payload.email());
        return repository.save(department);
    }
*/
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

    private void validateId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id cannot be null og blank");
        }
    }
}