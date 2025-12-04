package com.himmerland.hero.web;

import com.himmerland.hero.domain.departments.Department;
import com.himmerland.hero.service.departments.DepartmentService;
import com.himmerland.hero.service.departments.DepartmentDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin
public class DepartmentController {
    
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Department>> getAll() {
        return ResponseEntity.ok(departmentService.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Department> getById(@PathVariable String id) {
        return ResponseEntity.ok(departmentService.getDepartment(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Department> create(@RequestBody DepartmentDTO payload) {
        Department created = departmentService.createDepartment(payload);
        return ResponseEntity.created(Objects.requireNonNull(URI.create("/api/departments/" + created.getId())))
                .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                .body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Department> edit(@PathVariable String id, 
                @RequestBody DepartmentDTO payload) {
        Department edited = departmentService.editDepartment(id, payload);
        return ResponseEntity.ok(edited);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        departmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}