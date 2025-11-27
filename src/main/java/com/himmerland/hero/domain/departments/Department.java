package com.himmerland.hero.domain.departments;

import com.himmerland.hero.service.helperclasses.id.IdentifiableBase;

public class Department extends IdentifiableBase  {
    private String Id;
    private String Email;
    private boolean isActive;


    public Department (String Id, String Email) {
        this.Id = Id;
        this.Email = Email;
        this.isActive = true;
    }
    
    public String getId() {
        return Id;
    }

    public String getEmail() {
        return Email;
    }

    public void setId(String Id) {
        this.Id = Id;
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