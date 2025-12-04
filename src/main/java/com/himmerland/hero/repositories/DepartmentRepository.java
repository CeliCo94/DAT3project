package com.himmerland.hero.repositories;

import com.himmerland.hero.domain.departments.Department;
import java.nio.file.Path;
public class DepartmentRepository extends BaseRepository<Department>{

    public DepartmentRepository(Path dataDir) {
        super(dataDir, "departments", Department.class);
    }

    @Override
    protected Class<Department> getEntityClass() {
        return Department.class;
    }
    
}
