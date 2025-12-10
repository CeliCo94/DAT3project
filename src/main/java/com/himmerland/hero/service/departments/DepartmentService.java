package com.himmerland.hero.service.departments;

import com.himmerland.hero.domain.departments.Department;
import com.himmerland.hero.service.repositories.DepartmentRepository;

import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


@Service 
public class DepartmentService {

    private final DepartmentRepository repository;
    private static final Pattern VALID_ID = Pattern.compile("^[A-Za-z0-9_-]{3,32}$");

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
        validateId(id);
        return repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Department not found: " + id));
    }

    public Department createDepartment(DepartmentDTO payload) {
        validateId(payload.id());
        repository.findById(payload.id()).ifPresent(existing -> {
            throw new IllegalArgumentException("Department already exists: " + payload.id());
        });

        Department department = new Department(payload.id(), payload.email());
        if (payload.active() != null) {
            department.setActive(payload.active());
        }
        return repository.save(department);
    }

    public Department editDepartment(String id, DepartmentDTO payload) {
        Department existing = getDepartment(id);

        if (payload.email() != null && !payload.email().isBlank()) {
            existing.setEmail(payload.email());
        }

        if (payload.active() != null) {
            existing.setActive(payload.active());
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