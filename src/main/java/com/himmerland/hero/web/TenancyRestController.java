package com.himmerland.hero.web;

import com.himmerland.hero.service.tenancies.TenancyDTO;
import com.himmerland.hero.service.tenancies.TenancyService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteTenancy(@PathVariable String id) {
        tenancyService.deleteTenancy(id);
        return ResponseEntity.noContent().build();
    }
}
