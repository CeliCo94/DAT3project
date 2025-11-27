package com.himmerland.hero.web;

import com.himmerland.hero.domain.departments.Department;
import com.himmerland.hero.service.departments.DepartmentService;
import com.himmerland.hero.service.departments.DepartmentDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.file.Path;
import java.util.List;

@Controller
@RequestMapping("/api/departments")
@CrossOrigin
public class DepartmentController {
    
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        Path dataDir = Path.of("data");
        this.departmentService = new DepartmentService(dataDir);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Department>> getAll() {
        return ResponseEntity.ok(departmentService.findAll());
    }

    @GetMapping(value = "/{id}", produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Department> getById(@PathVariable String id) {
        return ResponseEntity.ok(departmentService.getDepartment(id));
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
            public ResponseEntity<Department> create(@RequestBody DepartmentDTO payload) {
                Department created = departmentService.createDepartment(new DepartmentDTO(payload.id(), payload.email(), payload.active()));
                return ResponseEntity.created(URI.create("/api/departments/" + created.getId())).contentType(MediaType.APPLICATION_JSON).body(created);
            }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Department> edit(@PathVariable String id, 
                @RequestBody DepartmentDTO payload) {
        Department edited = departmentService.editDepartment(id, new DepartmentDTO(payload.id(), payload.email(), payload.active()));
        return ResponseEntity.ok(edited);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        departmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    
}