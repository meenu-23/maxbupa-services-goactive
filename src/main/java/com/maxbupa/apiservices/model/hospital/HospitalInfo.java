package com.maxbupa.apiservices.model.hospital;

public class HospitalInfo {

    private String HospID;
    private String Title;
    private String Address;
    private String City;
    private String Hosp_State;
    private String Pin;
    private String Phone;
    private String phone2;
    private String Fax;
    private String Fax2;
    private String Email;
    private String Website;
    private String Description;
    private String OrderBy;
    private String Created;
    private String CreatedBy;
    private String Modified;
    private String ModifiedBy;
    private String ID;


    public HospitalInfo(String hospID, String title, String address, String city, String hosp_State, String pin, String phone, String phone2, String fax, String fax2, String email, String website, String description, String orderBy, String created, String createdBy, String modified, String modifiedBy, String ID) {
        HospID = hospID;
        Title = title;
        Address = address;
        City = city;
        Hosp_State = hosp_State;
        Pin = pin;
        Phone = phone;
        this.phone2 = phone2;
        Fax = fax;
        Fax2 = fax2;
        Email = email;
        Website = website;
        Description = description;
        OrderBy = orderBy;
        Created = created;
        CreatedBy = createdBy;
        Modified = modified;
        ModifiedBy = modifiedBy;
        this.ID = ID;
    }

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
