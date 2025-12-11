package com.himmerland.hero.domain.departments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.himmerland.hero.service.helperclasses.id.IdentifiableBase;

public class Department extends IdentifiableBase  {
    private String Name;
    private String Email;


    public Department() {
        super();
    }

    @JsonCreator
    public Department(@JsonProperty("id") String Name, @JsonProperty("email") String email) {
        super();
        this.Name = Name;
        this.Email = email;
        // Sync the Id with IdentifiableBase
        if (Name != null){
            super.setId(Name);
        }
    }
    
    public String getName() {
        return Name != null ? Name : super.getId();
    }

    public String getEmail() {
        return Email;
    }

    public void setName(String Name) {
        this.Name = Name;
        if (Name != null) {
            super.setId(Name);
        }
    }

    
    
}