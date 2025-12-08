package com.himmerland.hero.web;

import com.himmerland.hero.service.io.JsonStorage;
import com.himmerland.hero.service.io.StorageStrategy;
import com.himmerland.hero.domain.rules.Rule;
import com.himmerland.hero.domain.rules.RuleFactory;

import java.nio.file.Path;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/addRule")
@CrossOrigin
public class AddRuleController {

  RuleFactory ruleFactory;

  public AddRuleController(RuleFactory ruleFactory){
    this.ruleFactory = ruleFactory;
    System.out.println("ruleFactory instatiated");
  }

  @PostMapping(consumes = "application/json", produces = "application/json")
  public ResponseEntity<Rule> submitRule(@RequestBody RuleRequest body) {
    
    Rule rule = ruleFactory.create(body);
    System.out.println("Body in AddRuleController" + body);

    Path dataDir = Path.of("data");
    StorageStrategy<Rule> ruleStorage = new JsonStorage<>(dataDir, "Rules", Rule.class);

    System.out.println("Received rule: " + rule);
    System.out.println(rule.getName());
    System.out.println(rule.getDescription());

    ruleStorage.write(rule);

    return ResponseEntity.status(201).body(rule);
  }
}