package com.maxbupa.apiservices.services.productcalculator.goactive;

import com.maxbupa.apiservices.model.productcalculator.goactive.GoActiveResponse;
import org.springframework.stereotype.Service;

@Service
public abstract class GoActiveServiceImpl implements GoActiveService{

    private String componentId;
    private String productId;
    private String basePremiumEldestMember;
    private String deductibleMultiplicativeFactor;
    private String advantageDiscount;
    private String premiumPersonalAccident;
    private String premiumHealthCoach;
    private String discount;
    private String discountType;
    private String tax;

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setBasePremiumEldestMember(String basePremiumEldestMember) {
        this.basePremiumEldestMember = basePremiumEldestMember;
    }

    public void setDeductibleMultiplicativeFactor(String deductibleMultiplicativeFactor) {
        this.deductibleMultiplicativeFactor = deductibleMultiplicativeFactor;
    }

    public void setAdvantageDiscount(String advantageDiscount) {
        this.advantageDiscount = advantageDiscount;
    }

    public void setPremiumPersonalAccident(String premiumPersonalAccident) {
        this.premiumPersonalAccident = premiumPersonalAccident;
    }

    public void setPremiumHealthCoach(String premiumHealthCoach) {
        this.premiumHealthCoach = premiumHealthCoach;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    protected GoActiveResponse calPremium()
    {
        int premiumAfterDeductible = calculatePremiumAfterDeductible();
        int premiumAfterAdvantAGE = calculatePremiumAfterAdvantAGE(premiumAfterDeductible);
        int basePremium = premiumAfterAdvantAGE;
        int premiumPA = Integer.parseInt(premiumPersonalAccident);
        int healthCoachPremium = Integer.parseInt(premiumHealthCoach);
        int premiumAfterAllAdjustmentBeforeUnderwriting = calPremiumAfterAllAdjustmentBeforeUnderwriting(basePremium, premiumPA, healthCoachPremium);
        double discountAmount = calDiscount(premiumAfterAllAdjustmentBeforeUnderwriting);
        int finalPremiumBeforeTax = calFinalPremium(premiumAfterAllAdjustmentBeforeUnderwriting, discountAmount);
        int finalPremiumAfterTax = finalPremiumAfterTax(finalPremiumBeforeTax);
        GoActiveResponse goActiveResponse = new GoActiveResponse();
        goActiveResponse.setProductId(productId);
        goActiveResponse.setComponentId(componentId);
        goActiveResponse.setPremiumEldestMember(basePremiumEldestMember);
        goActiveResponse.setPremiumAfterDeductible(Integer.toString(premiumAfterDeductible));
        goActiveResponse.setPremiumAfterAdvantAGE(Integer.toString(premiumAfterAdvantAGE));
        goActiveResponse.setBasePremium(Integer.toString(basePremium));
        goActiveResponse.setPremiumPA(Integer.toString(premiumPA));
        goActiveResponse.setPremiumHealthCoach(Integer.toString(healthCoachPremium));
        goActiveResponse.setPremiumAfterAllAdjustmentBeforeUnderwriting(Integer.toString(premiumAfterAllAdjustmentBeforeUnderwriting));
        goActiveResponse.setDiscountType(discountType);
        int discountAmountRoundOff = (int) Math.round(discountAmount);
        goActiveResponse.setDiscount(Integer.toString(discountAmountRoundOff));
        goActiveResponse.setPremiumBeforeGst(Integer.toString(finalPremiumBeforeTax));
        goActiveResponse.setGst(tax);
        goActiveResponse.setPremiumAfterGst(Integer.toString(finalPremiumAfterTax));
        return goActiveResponse;
    }

    private int finalPremiumAfterTax(int finalPremium)
    {
        double taxVlue = Double.parseDouble(tax)/100;
        return (int) Math.round(finalPremium * (1 + taxVlue));
    }

    private int calFinalPremium(double doublePremiumAfterAllAdjustmentBeforeUnderwriting, double discountAmount)
    {
        double finalPremium = doublePremiumAfterAllAdjustmentBeforeUnderwriting - discountAmount;
        return (int) Math.round(finalPremium);

    }

    private double calDiscount(double premiumAfterAllAdjustmentBeforeUnderwriting)
    {
        double discountValue = Double.parseDouble(discount);
        double discnt = 1 - discountValue / 100;
        return premiumAfterAllAdjustmentBeforeUnderwriting * (1 - discnt);
    }


    private int calPremiumAfterAllAdjustmentBeforeUnderwriting(int basePremium, int premiumPA, int healthCoachPremium)
    {
        return basePremium + premiumPA + healthCoachPremium;
    }

    private int calculatePremiumAfterDeductible()
    {
        double doubleBasePremiumEldestMember = Double.parseDouble(basePremiumEldestMember);
        double doubleDeductibleMultiplicativeFactor = Double.parseDouble(deductibleMultiplicativeFactor);
        double dmf = doubleDeductibleMultiplicativeFactor / 100;
        return (int) Math.round(doubleBasePremiumEldestMember*dmf);
    }

    private double calculateAdvantAGE()
    {
        double doubleAdvantageDiscount = Double.parseDouble(advantageDiscount);
        double advantageDisct = doubleAdvantageDiscount /100;
        return 1-advantageDisct;
    }

    private int calculatePremiumAfterAdvantAGE(int premiumAfterDeductible)
    {
        Double advantAGE = calculateAdvantAGE();
        Double prmAfterDeductible = Double.valueOf(premiumAfterDeductible);
        return (int) Math.round(prmAfterDeductible*advantAGE);
    }

}
