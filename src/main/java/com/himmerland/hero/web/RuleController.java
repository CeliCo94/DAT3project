package com.himmerland.hero.web;

import com.himmerland.hero.Application;
import com.himmerland.hero.domain.rules.RuleThresholdHeat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/regler")
@CrossOrigin
public class RuleController {

    // Simple in-memory storage – disappears when server restarts
    private final List<RuleThresholdHeat> rules = new ArrayList<>();

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<RuleThresholdHeat> submitRule(@RequestBody RuleThresholdHeat rule) {
        System.out.println("Received rule: " + rule);

        // "Edit" behaviour: if a rule with same name exists, replace it
        rules.removeIf(r -> r.getName().equals(rule.getName()));
        rules.add(rule);

        // Run rule engine on test data (writes notifications.json)
        Application.evaluateRuleThresholdHeat(rule);

        return ResponseEntity.status(201).body(rule);
    }

    @GetMapping(produces = "application/json")
    public List<RuleThresholdHeat> getAllRules() {
        return rules;
    }
}