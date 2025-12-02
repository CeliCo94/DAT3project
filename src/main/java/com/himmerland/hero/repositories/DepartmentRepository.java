package com.himmerland.hero.repositories;

import com.himmerland.hero.domain.departments.Department;
import com.himmerland.hero.service.helperclasses.handlejson.ReadAllJsonToList;
import com.himmerland.hero.service.io.JsonStorage;
import com.himmerland.hero.service.io.StorageStrategy;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class DepartmentRepository {

    private final StorageStrategy<Department> storage;
    private final Path departmentsDir;

    public DepartmentRepository(Path dataDir) {
        this.departmentsDir = dataDir.resolve("departments");
        this.storage = new JsonStorage<>(dataDir, "departments", Department.class);
    }

    public Optional<Department> findById(String id) {
        return storage.read(id);
    }

    public Department save(Department department) {
        storage.write(department);
        return department;
    }

    public List<Department> findAll() {
        return ReadAllJsonToList.readAll(departmentsDir, Department.class);
    }

    public void deleteById(String id) {
        storage.delete(id);
    }

    public boolean doesIdExist(String id) {
        return storage.read(id).isPresent();
    }
    
}
