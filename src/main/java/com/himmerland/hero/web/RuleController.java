package com.himmerland.hero.web;

import com.himmerland.hero.domain.rules.Rule;
import com.himmerland.hero.domain.rules.RuleFactory;
import com.himmerland.hero.service.rules.RuleService;
import com.himmerland.hero.web.RuleRequest;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/rules")
@CrossOrigin
public class RuleController {

  private final RuleService ruleService;
  private final RuleFactory ruleFactory;

  public RuleController(RuleService ruleService, RuleFactory ruleFactory) {
        this.ruleService = ruleService;
        this.ruleFactory = ruleFactory;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rule> submitRule(@RequestBody RuleRequest body) {
        Rule rule = ruleFactory.create(body);
        Rule saved = ruleService.createRule(rule);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Rule>> getAllRules() {
        List<Rule> rules = ruleService.findAllRules();  
        return ResponseEntity.ok(rules);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rule> getRule(@PathVariable String id) {
        Rule rule = ruleService.getRule(id);
        return ResponseEntity.ok(rule);
    }

    @GetMapping("/history")
    public ResponseEntity<List<Rule>> getRulesHistory(
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        List<Rule> rules = ruleService.showHistoricRules(limit);
        return ResponseEntity.ok(rules);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rule> updateRule(@PathVariable String id, @RequestBody Rule body) {
        Rule updated = ruleService.updateRule(id, body);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Rule> toggleRule(@PathVariable String id) {
        Rule updated = ruleService.toggleRule(id);
        return ResponseEntity.ok(updated);
    }

}