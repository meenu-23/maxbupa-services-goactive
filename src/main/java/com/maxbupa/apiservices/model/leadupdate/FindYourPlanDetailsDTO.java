package com.maxbupa.apiservices.model.leadupdate;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class FindYourPlanDetailsDTO {

    @NotNull(message = "formId is a required field")
    private String formId;

    @NotNull(message = "personalizationId is a required field")
    private String personalizationId;

    private String formName;
    private String name;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dob;

    private String phoneNumber;
    private String buyingFor;

    @JsonFormat(locale = "en_IN",timezone = "Asia/Kolkata")
    private Date dateTime;

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getPersonalizationId() {
        return personalizationId;
    }

    public void setPersonalizationId(String personalizationId) {
        this.personalizationId = personalizationId;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonFormat(pattern = "dd/MM/yyyy")
    public Date getDob() {
        return dob;
    }

    @JsonFormat(pattern = "dd/MM/yyyy")
    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBuyingFor() {
        return buyingFor;
    }

    public void setBuyingFor(String buyingFor) {
        this.buyingFor = buyingFor;
    }

    @JsonFormat( locale = "en_IN",timezone = "Asia/Kolkata")
    public Date getDateTime() {
        return dateTime;
    }

    @JsonFormat( locale = "en_IN",timezone = "Asia/Kolkata")
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }


}
