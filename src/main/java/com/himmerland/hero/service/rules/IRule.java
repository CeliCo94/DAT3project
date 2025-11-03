package com.himmerland.hero.service.rules;

interface IRule {
    
    int testRule();
    void activateRule();
    void applyDescription(String description);
}
