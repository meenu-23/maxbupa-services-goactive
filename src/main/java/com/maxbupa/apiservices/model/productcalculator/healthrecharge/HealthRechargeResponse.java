package com.maxbupa.apiservices.model.productcalculator.healthrecharge;

public class HealthRechargeResponse {

    private String productId;
    private String componentId;
    private String basePremium;
    private String personalAccidentPremium;
    private String criticalIllnessPremium;
    private String modificationRoomRentPremium;
    private String total;
    private String discountType;
    private String discountPercentage;
    private String totalPremium;
    private String basePremiumAfterDiscount;
    private String tax;
    private String basePremiumAfterTax;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public String getBasePremium() {
        return basePremium;
    }

    public void setBasePremium(String basePremium) {
        this.basePremium = basePremium;
    }

    public String getPersonalAccidentPremium() {
        return personalAccidentPremium;
    }

    public void setPersonalAccidentPremium(String personalAccidentPremium) {
        this.personalAccidentPremium = personalAccidentPremium;
    }

    public String getCriticalIllnessPremium() {
        return criticalIllnessPremium;
    }

    public void setCriticalIllnessPremium(String criticalIllnessPremium) {
        this.criticalIllnessPremium = criticalIllnessPremium;
    }

    public String getModificationRoomRentPremium() {
        return modificationRoomRentPremium;
    }

    public void setModificationRoomRentPremium(String modificationRoomRentPremium) {
        this.modificationRoomRentPremium = modificationRoomRentPremium;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalPremium() {
        return totalPremium;
    }

    public void setTotalPremium(String totalPremium) {
        this.totalPremium = totalPremium;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getBasePremiumAfterDiscount() {
        return basePremiumAfterDiscount;
    }

    public void setBasePremiumAfterDiscount(String basePremiumAfterDiscount) {
        this.basePremiumAfterDiscount = basePremiumAfterDiscount;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getBasePremiumAfterTax() {
        return basePremiumAfterTax;
    }

    public void setBasePremiumAfterTax(String basePremiumAfterTax) {
        this.basePremiumAfterTax = basePremiumAfterTax;
    }

    public String getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
