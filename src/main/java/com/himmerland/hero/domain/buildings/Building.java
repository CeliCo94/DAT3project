package com.himmerland.hero.domain.buildings;

import com.himmerland.hero.service.helperclasses.id.IdentifiableBase;

public class Building extends IdentifiableBase {
    private String address = "testAddress";

    public Building() {}

    public String getAddress(){
        return this.address;
    }

}
