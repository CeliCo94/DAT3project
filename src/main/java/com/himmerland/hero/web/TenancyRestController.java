package com.himmerland.hero.web;

import com.himmerland.hero.service.tenancies.TenancyDTO;
import com.himmerland.hero.service.tenancies.TenancyService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tenancies")
@CrossOrigin
public class TenancyRestController {

    private final TenancyService tenancyService;

    public TenancyRestController(TenancyService tenancyService) {
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

        return ResponseEntity
            //.created(URI.create("/api/tenancies/" + created.id()))
            //.contentType(MediaType.APPLICATION_JSON)
            .status(HttpStatus.CREATED)
            .body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TenancyDTO> update(@PathVariable String id, @RequestBody TenancyDTO payload) {
        // Ensure the ID in the path is used
        TenancyDTO updatedDTO = new TenancyDTO(
            payload.departmentName(),   // departmentId
            payload.tennancyNumber(), // tennancyNumber (same typo as in DTO)
            payload.address(),        // address
            payload.city(),           // city
            payload.postalCode()     // postalCode
        );

        return ResponseEntity.ok(tenancyService.updateTenancy(updatedDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteTenancy(@PathVariable String id) {
        tenancyService.deleteTenancy(id);
        return ResponseEntity.noContent().build();
    }
}
