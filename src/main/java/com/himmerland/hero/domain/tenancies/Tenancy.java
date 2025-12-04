package com.himmerland.hero.domain.tenancies;

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

    public Tenancy(String meterNumber, String departmentId, String tennancyNumber, String address, String city, String postalCode) {
        this.MeterNumber = meterNumber;
        this.DepartmentId = departmentId;
        this.TennancyNumber = tennancyNumber;
        this.Address = address;
        this.City = city;
        this.PostalCode = postalCode;
        this.isActive = true;
    }

    public String getId() {
        return Id;
    }
    public String getMeterNumber() {
        return MeterNumber;
    }
    
    public String getDepartmentId() {
        return DepartmentId;
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
    }
    public void setActive(boolean isActive) {
        this.isActive = isActive;
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
}