package com.himmerland.hero.service.ruleEngine;

import com.himmerland.hero.domain.buildings.Building;
public class RuleContext {

    private Building building;

    public RuleContext(){
        this.building = new Building();
    }

    public String getContextInfo(){
        return building.getAddress();
    }
}
