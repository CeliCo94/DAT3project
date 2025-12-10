package com.himmerland.hero.service.repositories;

import com.himmerland.hero.domain.departments.Department;

import java.nio.file.Path;

import org.springframework.stereotype.Repository;
@Repository
public class DepartmentRepository extends BaseRepository<Department>{

    public DepartmentRepository(Path dataDir) {
        super(dataDir, "departments", Department.class);
    }

    @Override
    protected Class<Department> getEntityClass() {
        return Department.class;
    }
    
}
