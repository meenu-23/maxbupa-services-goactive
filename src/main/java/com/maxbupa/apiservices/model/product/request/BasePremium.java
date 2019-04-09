package com.maxbupa.apiservices.model.product.request;

public class BasePremium {

    private String productId;
    private String variant;
    private String familyCombination;
    private String zone;
    private String age;
    private String sumInsured;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getFamilyCombination() {
        return familyCombination;
    }

    public void setFamilyCombination(String familyCombination) {
        this.familyCombination = familyCombination;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSumInsured() {
        return sumInsured;
    }

    public void setSumInsured(String sumInsured) {
        this.sumInsured = sumInsured;
    }
}
