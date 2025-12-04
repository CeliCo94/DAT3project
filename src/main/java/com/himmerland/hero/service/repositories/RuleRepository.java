package com.himmerland.hero.service.repositories;

import com.himmerland.hero.domain.rules.Rule;
import com.himmerland.hero.service.io.JsonStorage;
import com.himmerland.hero.service.io.StorageStrategy;
import com.himmerland.hero.service.helperclasses.handlejson.ReadAllJsonToList;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class RuleRepository {

    private final StorageStrategy<Rule> storage;
    private final Path rulesDir;

    public RuleRepository(Path dataDir) {
        this.rulesDir = dataDir.resolve("Rules");
        this.storage = new JsonStorage<>(dataDir, "Rules", Rule.class);
    }

    public Optional<Rule> findById(String id) {
        return storage.read(id);
    }

    public Rule save(Rule rule) {
        storage.write(rule);
        return rule;
    }

    public List<Rule> findAll() {
        return ReadAllJsonToList.readAll(rulesDir, Rule.class);
    }

    public void deleteById(String id) {
        storage.delete(id);
    }

    public List<Rule> findActive() {
        return findAll().stream()
                .filter(Rule::isActive)
                .toList();
    }

    public List<Rule> findLastN(int amount) {
        List<Rule> all = findAll();
        int fromIndex = Math.max(0, all.size() - amount);
        return all.subList(fromIndex, all.size());
    }
}
