package com.himmerland.hero.domain.tenancies;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.himmerland.hero.service.helperclasses.id.IdentifiableBase;

public class Tenancy extends IdentifiableBase {
    private String Id;
    private String DepartmentId;
    private String MeterNumber;
    private String TennancyNumber;
    private String Address;
    private String City;
    private String PostalCode;
    private boolean isActive;

    public Tenancy() {
        super();
        this.isActive = true;
    }

    // Convenience constructor for tests (without id - will use auto-generated from IdentifiableBase)
    public Tenancy(String meterNumber, String departmentId, String tennancyNumber, String address, String city, String postalCode) {
        super();
        this.MeterNumber = meterNumber;
        this.DepartmentId = departmentId;
        this.TennancyNumber = tennancyNumber;
        this.Address = address;
        this.City = city;
        this.PostalCode = postalCode;
        this.isActive = true;
    }

    @JsonCreator
    public Tenancy(@JsonProperty("id") String id, @JsonProperty("meterNumber") String meterNumber, @JsonProperty("departmentId") String departmentId, @JsonProperty("tennancyNumber") String tennancyNumber, @JsonProperty("address") String address, @JsonProperty("city") String city, @JsonProperty("postalCode") String postalCode) {
        super();
        this.Id = id;
        this.MeterNumber = meterNumber;
        this.DepartmentId = departmentId;
        this.TennancyNumber = tennancyNumber;
        this.Address = address;
        this.City = city;
        this.PostalCode = postalCode;
        this.isActive = true;
        // Sync the Id with IdentifiableBase
        if (id != null) {
            super.setId(id);
        }
    }
    
    public String getId() {
        return Id != null ? Id : super.getId();
    }
    
    public String getDepartmentId() {
        return DepartmentId;
    }
    
    public String getMeterNumber() {
        return MeterNumber;
    }
    
    public String getTennancyNumber() {
        return TennancyNumber;
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
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setId(String id) {
        this.Id = id;
        if (id != null) {
            super.setId(id);
        }
    }
    
    public void setMeterNumber(String meterNumber) {
        this.MeterNumber = meterNumber;
    }
    
    public void setDepartmentId(String departmentId) {
        this.DepartmentId = departmentId;
    }
    
    public void setTennancyNumber(String tennancyNumber) {
        this.TennancyNumber = tennancyNumber;
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
    
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}