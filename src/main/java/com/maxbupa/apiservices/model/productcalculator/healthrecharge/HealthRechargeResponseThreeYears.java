package com.maxbupa.apiservices.model.productcalculator.healthrecharge;

public class HealthRechargeResponseThreeYears {

    private HealthRechargeResponse firstYearPremium;
    private HealthRechargeResponse secondYearPremium;
    private HealthRechargeResponse thirdYearPremium;

    public HealthRechargeResponse getFirstYearPremium() {
        return firstYearPremium;
    }

    public void setFirstYearPremium(HealthRechargeResponse firstYearPremium) {
        this.firstYearPremium = firstYearPremium;
    }

    public HealthRechargeResponse getSecondYearPremium() {
        return secondYearPremium;
    }

    public void setSecondYearPremium(HealthRechargeResponse secondYearPremium) {
        this.secondYearPremium = secondYearPremium;
    }

    public HealthRechargeResponse getThirdYearPremium() {
        return thirdYearPremium;
    }

    public void setThirdYearPremium(HealthRechargeResponse thirdYearPremium) {
        this.thirdYearPremium = thirdYearPremium;
    }
}