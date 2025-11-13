package com.himmerland.hero.domain.rules;

interface IRule {
    
    int testRule();
    void activateRule();
    void applyDescription(String description);
}
