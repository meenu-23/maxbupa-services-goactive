package com.maxbupa.apiservices.model.leadupdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@ApiModel(description = "Get your Quote Form fill Details")
public class GetYourQuoteFormDetailsDTO {

    @NotNull(message = "formId is a required field")
    private String formId;

    @NotNull(message = "personalizationId is a required field")
    private String personalizationId;

    private String formName;
    private String name;

    @Past
    @ApiModelProperty(notes="Birth date should be in the past. Date format should be yyyy-MM-dd")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dob;

    private String phoneNumber;
    private String city;
    private String noOfAdults;
    private String noOfChildren;
    private String sumInsured;
    private String annualFamilyIncome;
    private String productName;
    private String planType;
    private String variant;
    private String payablePremium;
    private String quoteId;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getSumInsured() {
        return sumInsured;
    }

    public void setSumInsured(String sumInsured) {
        this.sumInsured = sumInsured;
    }

    public String getPayablePremium() {
        return payablePremium;
    }

    public void setPayablePremium(String payablePremium) {
        this.payablePremium = payablePremium;
    }

    @JsonFormat( locale = "en_IN",timezone = "Asia/Kolkata")
    public Date getDateTime() {
        return dateTime;
    }

    @JsonFormat( locale = "en_IN",timezone = "Asia/Kolkata")
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getNoOfAdults() {
        return noOfAdults;
    }

    public void setNoOfAdults(String noOfAdults) {
        this.noOfAdults = noOfAdults;
    }

    public String getNoOfChildren() {
        return noOfChildren;
    }

    public void setNoOfChildren(String noOfChildren) {
        this.noOfChildren = noOfChildren;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public String getAnnualFamilyIncome() {
        return annualFamilyIncome;
    }

    public void setAnnualFamilyIncome(String annualFamilyIncome) {
        this.annualFamilyIncome = annualFamilyIncome;
    }
}
