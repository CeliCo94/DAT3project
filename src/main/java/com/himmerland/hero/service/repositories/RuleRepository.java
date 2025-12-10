package com.himmerland.hero.service.repositories;

import org.springframework.stereotype.Repository;
import com.himmerland.hero.domain.rules.Rule;
import java.nio.file.Path;
import java.util.List;

@Repository
public class RuleRepository extends BaseRepository<Rule>{

    public RuleRepository(Path dataDir) {
        super(dataDir, "Rules", Rule.class);
    }

    public List<Rule> findActive() {
        return findAll().stream()
                .filter(Rule::isActive)
                .toList();
    }

    public List<Rule> findRuleFromType(String type) {
        return findAll().stream()
            .filter(rule -> type.equals(rule.getConsumptionType()))
            .toList();
    }

    public List<Rule> findLastN(int amount) {
        List<Rule> all = findAll();
        int fromIndex = Math.max(0, all.size() - amount);
        return all.subList(fromIndex, all.size());
    }

    @Override
    protected Class<Rule> getEntityClass() {
        return Rule.class;
    }
}
