package com.maxbupa.apiservices.model.productcalculator.healthrecharge;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class HealthRechargeModel {

    private String componentId;

    @NotNull(message = "productId is a required field")
    @NotEmpty(message = "productId cannot be Empty")
    @NotBlank(message = "productId cannot be Blank")
    private String productId;

    @NotNull(message = "si is a required field")
    @NotEmpty(message = "si cannot be Empty")
    @NotBlank(message = "si cannot be Blank")
    private String si;

    @NotNull(message = "deductible is a required field")
    @NotEmpty(message = "deductible cannot be Empty")
    @NotBlank(message = "deductible cannot be Blank")
    private String deductible;

    @NotNull(message = "familyCombination is a required field")
    @NotEmpty(message = "familyCombination cannot be Empty")
    @NotBlank(message = "familyCombination cannot be Blank")
    private String familyCombination;

    @NotNull(message = "ageOfEldestMember is a required field")
    @NotEmpty(message = "ageOfEldestMember cannot be Empty")
    @NotBlank(message = "ageOfEldestMember cannot be Blank")
    private String ageOfEldestMember;

    @NotNull(message = "ageOfSpouse is a required field")
    @NotEmpty(message = "ageOfSpouse cannot be Empty")
    @NotBlank(message = "ageOfSpouse cannot be Blank")
    private String ageOfSpouse;

    @NotNull(message = "ageOfSpouse is a required field")
    @NotEmpty(message = "ageOfSpouse cannot be Empty")
    @NotBlank(message = "ageOfSpouse cannot be Blank")
    private String ageOfPrimaryInsured;

    @NotNull(message = "personalAccident is a required field")
    @NotEmpty(message = "personalAccident cannot be Empty")
    @NotBlank(message = "personalAccident cannot be Blank")
    private String personalAccident;

    @NotNull(message = "paSi is a required field")
    @NotEmpty(message = "paSi cannot be Empty")
    @NotBlank(message = "paSi cannot be Blank")
    private String paSi;

    @NotNull(message = "ci is a required field")
    @NotEmpty(message = "ci cannot be Empty")
    @NotBlank(message = "ci cannot be Blank")
    private String ci;

    @NotNull(message = "ciSi is a required field")
    @NotEmpty(message = "ciSi cannot be Empty")
    @NotBlank(message = "ciSi cannot be Blank")
    private String ciSi;

    @NotNull(message = "modificationRoomRent is a required field")
    @NotEmpty(message = "modificationRoomRent cannot be Empty")
    @NotBlank(message = "modificationRoomRent cannot be Blank")
    private String modificationRoomRent;

    @NotNull(message = "discount is a required field")
    @NotEmpty(message = "discount cannot be Empty")
    @NotBlank(message = "discount cannot be Blank")
    private String discount;

    @NotNull(message = "variant is a required field")
    @NotEmpty(message = "variant cannot be Empty")
    @NotBlank(message = "variant cannot be Blank")
    private String variant;

    @NotNull(message = "zone is a required field")
    @NotEmpty(message = "zone cannot be Empty")
    @NotBlank(message = "zone cannot be Blank")
    private String zone;

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public String getSi() {
        return si;
    }

    public void setSi(String si) {
        this.si = si;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDeductible() {
        return deductible;
    }

    public void setDeductible(String deductible) {
        this.deductible = deductible;
    }

    public String getFamilyCombination() {
        return familyCombination;
    }

    public void setFamilyCombination(String familyCombination) {
        this.familyCombination = familyCombination;
    }

    public String getAgeOfEldestMember() {
        return ageOfEldestMember;
    }

    public void setAgeOfEldestMember(String ageOfEldestMember) {
        this.ageOfEldestMember = ageOfEldestMember;
    }

    public String getAgeOfSpouse() {
        return ageOfSpouse;
    }

    public void setAgeOfSpouse(String ageOfSpouse) {
        this.ageOfSpouse = ageOfSpouse;
    }

    public String getAgeOfPrimaryInsured() {
        return ageOfPrimaryInsured;
    }

    public void setAgeOfPrimaryInsured(String ageOfPrimaryInsured) {
        this.ageOfPrimaryInsured = ageOfPrimaryInsured;
    }

    public String getPersonalAccident() {
        return personalAccident;
    }

    public void setPersonalAccident(String personalAccident) {
        this.personalAccident = personalAccident;
    }

    public String getPaSi() {
        return paSi;
    }

    public void setPaSi(String paSi) {
        this.paSi = paSi;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getCiSi() {
        return ciSi;
    }

    public void setCiSi(String ciSi) {
        this.ciSi = ciSi;
    }

    public String getModificationRoomRent() {
        return modificationRoomRent;
    }

    public void setModificationRoomRent(String modificationRoomRent) {
        this.modificationRoomRent = modificationRoomRent;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}
