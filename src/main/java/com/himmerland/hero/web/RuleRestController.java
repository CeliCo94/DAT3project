package com.himmerland.hero.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.himmerland.hero.service.rules.RuleThresholdHeat;

// Render HTML content 
@RestController
public class RuleRestController {
    // Maybe her mit problem er?
    @GetMapping("/Regler") 
    public String ruleForm(Model model) {
        model.addAttribute("rule", new RuleThresholdHeat());
        return "rule";
    }

    @PostMapping("/Regler") 
    public String ruleSubmit(@ModelAttribute RuleThresholdHeat rule, Model model) {
        model.addAttribute("rule", rule);
        return "result";
    }
}
