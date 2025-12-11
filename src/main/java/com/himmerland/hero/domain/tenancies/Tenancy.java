package com.himmerland.hero.domain.tenancies;

import com.himmerland.hero.service.helperclasses.id.IdentifiableBase;

public class Tenancy extends IdentifiableBase {
    private String DepartmentName;
    private String Address;
    private String City;
    private String PostalCode;

    public Tenancy() {
        super();
    }

    // Convenience constructor for tests (without id - will use auto-generated from IdentifiableBase)
    public Tenancy(String departmentName, String address, String city, String postalCode) {
        super();
        this.DepartmentName = departmentName;
        this.Address = address;
        this.City = city;
        this.PostalCode = postalCode;
    }
    
    public String getDepartmentName() {
        return DepartmentName;
    }
    
    public String getAddress() {
        return Address;
    }
    
    public String getCity() {
        return City;
    }
    
    public String getPostalCode() {
        return PostalCode;
    }
    
    public void setDepartmentName(String departmentName) {
        this.DepartmentName = departmentName;
    }
    
    public void setAddress(String address) {
        this.Address = address;
    }
    
    public void setCity(String city) {
        this.City = city;
    }
    
    public void setPostalCode(String postalCode) {
        this.PostalCode = postalCode;
    }
}