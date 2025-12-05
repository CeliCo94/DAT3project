package com.himmerland.hero.domain.departments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.himmerland.hero.service.helperclasses.id.IdentifiableBase;

public class Department extends IdentifiableBase  {
    private String Id;
    private String Email;
    private boolean isActive;


    public Department() {
        super();
        this.isActive = true;
    }

    @JsonCreator
    public Department(@JsonProperty("id") String id, @JsonProperty("email") String email) {
        super();
        this.Id = id;
        this.Email = email;
        this.isActive = true;
        // Sync the Id with IdentifiableBase
        if (Id != null){
            super.setId(Id);
        }
    }
    
    public String getId() {
        return Id != null ? Id : super.getId();
    }

    public String getEmail() {
        return Email;
    }

    public void setId(String Id) {
        this.Id = Id;
        if (Id != null) {
            super.setId(Id);
        }
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    
    
}