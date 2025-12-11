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
    public Department(@JsonProperty("Name") String Name, @JsonProperty("email") String email) {
        super();
        this.Name = Name;
        this.Email = email;
    }
/* 
    public Department(String Name, String email) {
        super();
        this.Name = Name;
        this.Email = email;
    }
    */
    public String getName() {
        return Name;
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

    public void setEmail(String Email) {
        this.Email = Email;
    }
    
}