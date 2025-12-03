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
        this.storage = new JsonStorage<>(dataDir, "Rules", Rule.class);
        this.rulesDir = dataDir.resolve("Rules");
    }

    public void save(Rule rule) {
        storage.write(rule);
    }

    public Optional<Rule> findById(String id) {
        return storage.read(id);
    }

    public List<Rule> findAll() {
        return ReadAllJsonToList.readAll(rulesDir, Rule.class);
    }

    public List<Rule> findActive() {
        return findAll();
    }

    public List<Rule> findLastN(int amount) {
        List<Rule> all = findAll();
        int fromIndex = Math.max(0, all.size() - amount);
        return all.subList(fromIndex, all.size());
    }
}
