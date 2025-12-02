package com.himmerland.hero.service.departments;

import com.himmerland.hero.domain.departments.Department;
import com.himmerland.hero.service.helperclasses.handlejson.ReadAllJsonToList;
import com.himmerland.hero.service.io.JsonStorage;
import com.himmerland.hero.service.io.StorageStrategy;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service 
public class DepartmentService {

    private final StorageStrategy<Department> storage;
    private final Path departmentsDir;
    private static final Pattern VALID_ID = Pattern.compile("^[A-Za-z0-9_-]{3,32}$");

    public DepartmentService(Path dataDir) {
        this.departmentsDir = dataDir.resolve("departments");
        this.storage = new JsonStorage<>(dataDir, "departments", Department.class);
    }

    public List<Department> findAll() {
        return ReadAllJsonToList.readAll(departmentsDir, Department.class);
    }

    public Optional<Department> findById(String id) {
        return storage.read(id);
    }

    public Department save(Department department) {
        storage.write(department);
        return department;
    }

    public void delete(String id) {
        storage.delete(id);
    }

    public Department getDepartment(String id) {
        validateId(id);
        return storage.read(id)
            .orElseThrow(() -> new IllegalArgumentException("Department not found: " + id));
        
    }
    public Department createDepartment(DepartmentDTO payload) {
        validateId(payload.id());
        storage.read(payload.id()) .ifPresent(existing -> {
            throw new IllegalArgumentException("Department already exists: " + payload.id());
        });

        Department department = new Department(payload.id(), payload.email());
        if (payload.active() != null) {
            department.setActive(payload.active());
        }
        return save(department);
    }

    public Department editDepartment(String id, DepartmentDTO payload) {
        Department existing = getDepartment(id);

        if (payload.email() != null && !payload.email().equals(id)) {
            validateId(payload.id());
            return storage.read(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found: " + id));
        }

        if (payload.email() != null && !payload.email().isBlank()) {
            existing.setEmail(payload.email());
        }

        if (payload.active() != null) {
            existing.setActive(payload.active());
        }
        return save(existing);
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