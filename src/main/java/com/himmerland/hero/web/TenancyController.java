package com.himmerland.hero.web;

import com.himmerland.hero.service.tenancies.TenancyDTO;
import com.himmerland.hero.service.tenancies.TenancyService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/tenancies")
@CrossOrigin
public class TenancyController {

    private final TenancyService tenancyService;

    public TenancyController(TenancyService tenancyService) {
        this.tenancyService = tenancyService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TenancyDTO>> getAll() {
        return ResponseEntity.ok(tenancyService.findAll());
    }
    
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TenancyDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(tenancyService.getTenancy(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TenancyDTO> create(@RequestBody TenancyDTO payload) {
        TenancyDTO created = tenancyService.createTenancy(payload);
        return ResponseEntity.created(Objects.requireNonNull(URI.create("/api/tenancies/" + created.id())))
            .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
            .body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TenancyDTO> update(@PathVariable String id, @RequestBody TenancyDTO payload) {
        // Ensure the ID in the path matches the DTO
        // The service will handle departmentId/departmentName resolution
        TenancyDTO updatedDTO = new TenancyDTO(
            id, // Use path variable ID
            payload.meterNumber(),
            payload.departmentId(), // Service will resolve this if needed
            payload.departmentName(), // Service will use this if departmentId is null
            payload.tenancyNumber(),
            payload.address(),
            payload.city(),
            payload.postalCode()
        );
        return ResponseEntity.ok(tenancyService.updateTenancy(updatedDTO));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteTenancy(@PathVariable String id) {
        tenancyService.deleteTenancy(id);
        return ResponseEntity.noContent().build();
    }
}