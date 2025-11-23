package com.himmerland.hero.domain.rules;

import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.domain.notifications.Notification;
import com.himmerland.hero.domain.rules.RuleContext;
import java.util.List;

interface IRule {
    
    int testRule(List<Measurement> measurements);
    void activateRule();
    void applyDescription(String description);

    boolean isBrokenBy(Measurement m, RuleContext ctx);
    Notification buildNotification(Measurement m, RuleContext ctx);
    int getDuration();
}
