package com.himmerland.hero.web;

import com.himmerland.hero.domain.tenancies.Tenancy;
import com.himmerland.hero.service.tenancies.TenancyDTO;
import com.himmerland.hero.service.tenancies.TenancyService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/tennancies")
@CrossOrigin
public class TenancyController {

    private final TenancyService tennancyService;

    public TenancyController() {
        Path dataDir = Path.of("data");
        this.tennancyService = new TenancyService(dataDir);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tenancy> getById(@PathVariable String id) {
        return ResponseEntity.ok(tennancyService.getTenancy(id));
    }

    @GetMapping(value = "/{id}/edit", produces = MediaType.APPLICATION_JSON_VALUE) 
    public ResponseEntity<Tenancy> showEditForm(@PathVariable String id) {
        return ResponseEntity.ok(tennancyService.getTenancy(id));
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tenancy> update(@PathVariable String id, @RequestBody TenancyDTO payload) {
        return ResponseEntity.ok(tennancyService.updateTenancy(payload));
    }
    @PostMapping(value = "/{id}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteTenancy(@PathVariable String id) {
        tennancyService.deleteTenancy(id);
        return ResponseEntity.noContent().build();
    }
    
    
}