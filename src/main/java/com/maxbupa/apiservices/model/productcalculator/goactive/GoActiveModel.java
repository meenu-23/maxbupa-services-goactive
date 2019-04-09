package com.maxbupa.apiservices.model.productcalculator.goactive;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class GoActiveModel {

    private String componentId;

    @NotNull(message = "productId is a required field")
    @NotEmpty(message = "productId cannot be Empty")
    @NotBlank(message = "productId cannot be Blank")
    private String productId;

    @NotNull(message = "advantageDiscountFlag is a required field")
    @NotEmpty(message = "advantageDiscountFlag cannot be Empty")
    @NotBlank(message = "advantageDiscountFlag cannot be Blank")
    private String advantageDiscountFlag;

    @NotNull(message = "variant is a required field")
    @NotEmpty(message = "variant cannot be Empty")
    @NotBlank(message = "variant cannot be Blank")
    private String variant;

    @NotNull(message = "sumInsured is a required field")
    @NotEmpty(message = "sumInsured cannot be Empty")
    @NotBlank(message = "sumInsured cannot be Blank")
    private String sumInsured;

    @NotNull(message = "familyCombination is a required field")
    @NotEmpty(message = "familyCombination cannot be Empty")
    @NotBlank(message = "familyCombination cannot be Blank")
    private String familyCombination;

    @NotNull(message = "ageOfEldestMember is a required field")
    @NotEmpty(message = "ageOfEldestMember cannot be Empty")
    @NotBlank(message = "ageOfEldestMember cannot be Blank")
    private String ageOfEldestMember;

    @NotNull(message = "ageOfSecondMember is a required field")
    @NotEmpty(message = "ageOfSecondMember cannot be Empty")
    @NotBlank(message = "ageOfSecondMember cannot be Blank")
    private String ageOfSecondMember;

    @NotNull(message = "zone is a required field")
    @NotEmpty(message = "zone cannot be Empty")
    @NotBlank(message = "zone cannot be Blank")
    private String zone;

    @NotNull(message = "deductible is a required field")
    @NotEmpty(message = "deductible cannot be Empty")
    @NotBlank(message = "deductible cannot be Blank")
    private String deductible;

    @NotNull(message = "personalAccidentOptionalCover is a required field")
    @NotEmpty(message = "personalAccidentOptionalCover cannot be Empty")
    @NotBlank(message = "personalAccidentOptionalCover cannot be Blank")
    private String personalAccidentOptionalCover;

    @NotNull(message = "personalAccidentSumInsured is a required field")
    @NotEmpty(message = "personalAccidentSumInsured cannot be Empty")
    @NotBlank(message = "personalAccidentSumInsured cannot be Blank")
    private String personalAccidentSumInsured;

    @NotNull(message = "healthCoach is a required field")
    @NotEmpty(message = "healthCoach cannot be Empty")
    @NotBlank(message = "healthCoach cannot be Blank")
    private String healthCoach;

    @NotNull(message = "discount is a required field")
    @NotEmpty(message = "discount cannot be Empty")
    @NotBlank(message = "discount cannot be Blank")
    private String discount;

    private String iProtect;

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getAdvantageDiscountFlag() {
        return advantageDiscountFlag;
    }

    public void setAdvantageDiscountFlag(String advantageDiscountFlag) {
        this.advantageDiscountFlag = advantageDiscountFlag;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getSumInsured() {
        return sumInsured;
    }

    public void setSumInsured(String sumInsured) {
        this.sumInsured = sumInsured;
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

    public String getAgeOfSecondMember() {
        return ageOfSecondMember;
    }

    public void setAgeOfSecondMember(String ageOfSecondMember) {
        this.ageOfSecondMember = ageOfSecondMember;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getDeductible() {
        return deductible;
    }

    public void setDeductible(String deductible) {
        this.deductible = deductible;
    }

    public String getPersonalAccidentOptionalCover() {
        return personalAccidentOptionalCover;
    }

    public void setPersonalAccidentOptionalCover(String personalAccidentOptionalCover) {
        this.personalAccidentOptionalCover = personalAccidentOptionalCover;
    }

    public String getPersonalAccidentSumInsured() {
        return personalAccidentSumInsured;
    }

    public void setPersonalAccidentSumInsured(String personalAccidentSumInsured) {
        this.personalAccidentSumInsured = personalAccidentSumInsured;
    }

    public String getHealthCoach() {
        return healthCoach;
    }

    public void setHealthCoach(String healthCoach) {
        this.healthCoach = healthCoach;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getiProtect() {
        return iProtect;
    }

    public void setiProtect(String iProtect) {
        this.iProtect = iProtect;
    }
}
