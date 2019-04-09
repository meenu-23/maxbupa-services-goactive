package com.maxbupa.apiservices.model.productcalculator.goactive;

public class GoActiveResponse {

    private String productId;
    private String componentId;
    private String premiumEldestMember;
    private String premiumAfterDeductible;
    private String premiumAfterAdvantAGE;
    private String basePremium;
    private String premiumPA;
    private String premiumHealthCoach;
    private String premiumAfterAllAdjustmentBeforeUnderwriting;
    private String discountType;
    private String discount;
    private String premiumBeforeGst;
    private String gst;
    private String premiumAfterGst;

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

    public String getPremiumEldestMember() {
        return premiumEldestMember;
    }

    public void setPremiumEldestMember(String premiumEldestMember) {
        this.premiumEldestMember = premiumEldestMember;
    }

    public String getPremiumAfterDeductible() {
        return premiumAfterDeductible;
    }

    public void setPremiumAfterDeductible(String premiumAfterDeductible) {
        this.premiumAfterDeductible = premiumAfterDeductible;
    }

    public String getPremiumAfterAdvantAGE() {
        return premiumAfterAdvantAGE;
    }

    public void setPremiumAfterAdvantAGE(String premiumAfterAdvantAGE) {
        this.premiumAfterAdvantAGE = premiumAfterAdvantAGE;
    }

    public String getBasePremium() {
        return basePremium;
    }

    public void setBasePremium(String basePremium) {
        this.basePremium = basePremium;
    }

    public String getPremiumPA() {
        return premiumPA;
    }

    public void setPremiumPA(String premiumPA) {
        this.premiumPA = premiumPA;
    }

    public String getPremiumHealthCoach() {
        return premiumHealthCoach;
    }

    public void setPremiumHealthCoach(String premiumHealthCoach) {
        this.premiumHealthCoach = premiumHealthCoach;
    }

    public String getPremiumAfterAllAdjustmentBeforeUnderwriting() {
        return premiumAfterAllAdjustmentBeforeUnderwriting;
    }

    public void setPremiumAfterAllAdjustmentBeforeUnderwriting(String premiumAfterAllAdjustmentBeforeUnderwriting) {
        this.premiumAfterAllAdjustmentBeforeUnderwriting = premiumAfterAllAdjustmentBeforeUnderwriting;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPremiumBeforeGst() {
        return premiumBeforeGst;
    }

    public void setPremiumBeforeGst(String premiumBeforeGst) {
        this.premiumBeforeGst = premiumBeforeGst;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getPremiumAfterGst() {
        return premiumAfterGst;
    }

    public void setPremiumAfterGst(String premiumAfterGst) {
        this.premiumAfterGst = premiumAfterGst;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }
}
