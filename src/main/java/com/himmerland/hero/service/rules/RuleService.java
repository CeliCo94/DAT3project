package com.himmerland.hero.service.rules;

import com.himmerland.hero.domain.rules.Rule;
import com.himmerland.hero.service.repositories.RuleRepository;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public class RuleService {

    private final RuleRepository ruleRepository;

    public RuleService(RuleRepository ruleRepo) {
        this.ruleRepository = ruleRepo;
    }

    public List<Rule> showActiveRules() {
        return ruleRepository.findActive();
    }

    public List<Rule> findAllRules() {
        return ruleRepository.findAll();
    }

    public List<Rule> showHistoricRules(int amount) {
        return ruleRepository.findLastN(amount);
    }

    public Rule getRule(String id) {
        return ruleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rule not found: " + id));
    }

    public Rule createRule(Rule rule) {
        ruleRepository.save(rule);
        return rule;
    }

    public Rule updateRule(String id, Rule updated) {
        Rule existing = getRule(id); 

        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setDuration(updated.getDuration());
        existing.setActive(updated.isActive()); 

        ruleRepository.save(existing);

        return existing;
    }

    public Rule toggleRule(String id) {
        Rule rule = ruleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rule not found"));

        rule.setActive(!rule.isActive());
        return ruleRepository.save(rule);
    }
    
}