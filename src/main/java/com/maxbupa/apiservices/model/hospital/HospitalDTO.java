package com.maxbupa.apiservices.model.hospital;


import com.opencsv.bean.CsvBindByName;

public class HospitalDTO {

    @CsvBindByName
    private String HospID;

    @CsvBindByName
    private String Title;

    @CsvBindByName
    private String Address;

    @CsvBindByName
    private String City;

    @CsvBindByName
    private String Hosp_State;

    @CsvBindByName
    private String Pin;

    @CsvBindByName
    private String Phone;

    @CsvBindByName
    private String phone2;

    @CsvBindByName
    private String Fax;

    @CsvBindByName
    private String Fax2;

    @CsvBindByName
    private String Email;

    @CsvBindByName
    private String Website;

    @CsvBindByName
    private String Description;

    @CsvBindByName
    private String OrderBy;

    @CsvBindByName
    private double Latitude;

    @CsvBindByName
    private double Longitude;

    @CsvBindByName
    private String Created;

    @CsvBindByName(column = "Created By")
    private String CreatedBy;

    @CsvBindByName
    private String Modified;

    @CsvBindByName(column = "Modified By")
    private String ModifiedBy;

    @CsvBindByName
    private String ID;

    public String getHospID() {
        return HospID;
    }

    public void setHospID(String hospID) {
        HospID = hospID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getHosp_State() {
        return Hosp_State;
    }

    public void setHosp_State(String hosp_State) {
        Hosp_State = hosp_State;
    }

    public String getPin() {
        return Pin;
    }

    public void setPin(String pin) {
        Pin = pin;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String fax) {
        Fax = fax;
    }

    public String getFax2() {
        return Fax2;
    }

    public void setFax2(String fax2) {
        Fax2 = fax2;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getOrderBy() {
        return OrderBy;
    }

    public void setOrderBy(String orderBy) {
        OrderBy = orderBy;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getModified() {
        return Modified;
    }

    public void setModified(String modified) {
        Modified = modified;
    }

    public String getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        ModifiedBy = modifiedBy;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
