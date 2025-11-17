package com.himmerland.hero.web;

import com.himmerland.hero.Application;
import com.himmerland.hero.domain.rules.ruleThresholdHeat;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;


@Controller
@RequestMapping("/api/regler")
@CrossOrigin
public class RuleController {

  @PostMapping(consumes = "application/json", produces = "application/json")
  public ResponseEntity<ruleThresholdHeat> submitRule(@RequestBody ruleThresholdHeat rule) {
    System.out.println("Received rule: " + rule);
    //Application.evaluateRuleThresholdHeat(rule);
    return ResponseEntity.status(201).body(rule);
  }
}
