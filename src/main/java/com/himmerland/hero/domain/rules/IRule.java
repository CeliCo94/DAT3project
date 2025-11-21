package com.himmerland.hero.domain.rules;

import com.himmerland.hero.domain.measurements.Measurement;
import java.util.List;

interface IRule {
    
    int testRule(List<Measurement> measurements);
    void activateRule();
    void applyDescription(String description);
}
