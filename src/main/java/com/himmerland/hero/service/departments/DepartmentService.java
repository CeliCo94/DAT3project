package com.himmerland.hero.service.departments;

import com.himmerland.hero.domain.departments.Department;
import com.himmerland.hero.service.repositories.DepartmentRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service 
public class DepartmentService {

    private final DepartmentRepository repository;

    public DepartmentService(DepartmentRepository depRepo) {
        this.repository = depRepo;
    }

    public List<Department> findAll() {
        return repository.findAll();
    }

    public Optional<Department> findById(String id) {
        return repository.findById(id);
    }

    public Department save(Department department) {
        return repository.save(department);
    }

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

    public Department createDepartment(DepartmentDTO payload) {
        // Create department without ID - IdentifiableBase will auto-generate UUID
        Department department = new Department(null, payload.name(), payload.email());
        
        // If ID was provided in payload, use it (for backward compatibility or specific cases)
        if (payload.id() != null && !payload.id().isBlank()) {
            // Check if ID already exists
            repository.findById(payload.id()).ifPresent(existing -> {
                throw new IllegalArgumentException("Department already exists: " + payload.id());
            });
            department.setId(payload.id());
        }
        
        return repository.save(department);
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
}