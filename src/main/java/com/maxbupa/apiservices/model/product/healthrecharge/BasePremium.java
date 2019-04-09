package com.maxbupa.apiservices.model.product.healthrecharge;

public class BasePremium {

    private String sumInsured;
    private String deductible;
    private String familyCombination;
    private String ageEldestMember;
    private String ageSpouse;
    private int personalAccidentFlag;
    private int ciFlag;
    private int modRoomRentFlag;
    private int discountFlag;

    public String getSumInsured() {
        return sumInsured;
    }

    public void setSumInsured(String sumInsured) {
        this.sumInsured = sumInsured;
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

    public String getAgeEldestMember() {
        return ageEldestMember;
    }

    public void setAgeEldestMember(String ageEldestMember) {
        this.ageEldestMember = ageEldestMember;
    }

    public String getAgeSpouse() {
        return ageSpouse;
    }

    public void setAgeSpouse(String ageSpouse) {
        this.ageSpouse = ageSpouse;
    }

    public int getPersonalAccidentFlag() {
        return personalAccidentFlag;
    }

    public void setPersonalAccidentFlag(int personalAccidentFlag) {
        this.personalAccidentFlag = personalAccidentFlag;
    }

    public int getCiFlag() {
        return ciFlag;
    }

    public void setCiFlag(int ciFlag) {
        this.ciFlag = ciFlag;
    }

    public int getModRoomRentFlag() {
        return modRoomRentFlag;
    }

    public void setModRoomRentFlag(int modRoomRentFlag) {
        this.modRoomRentFlag = modRoomRentFlag;
    }

    public int getDiscountFlag() {
        return discountFlag;
    }

    public void setDiscountFlag(int discountFlag) {
        this.discountFlag = discountFlag;
    }
}
