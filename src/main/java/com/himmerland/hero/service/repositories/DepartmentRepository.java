package com.himmerland.hero.service.repositories;

import com.himmerland.hero.domain.departments.Department;

import java.nio.file.Path;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class DepartmentRepository extends BaseRepository<Department>{

    public DepartmentRepository(Path dataDir) {
        super(dataDir, "departments", Department.class);
    }

    public Optional<Department> getFromName(String name) {
        return findAll().stream()
              .filter(d -> d.getName().equals(name))
              .findFirst();
    }

    @Override
    protected Class<Department> getEntityClass() {
        return Department.class;
    }
    
}
