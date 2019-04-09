package com.maxbupa.apiservices.model.productcalculator.generalcalculator;

import com.maxbupa.apiservices.model.productcalculator.goactive.GoActiveModel;
import com.maxbupa.apiservices.model.productcalculator.healthrecharge.HealthRechargeModel;

import java.util.List;

public class GeneralCalculator {

    private List<GoActiveModel> goActiveModels;
    private List<HealthRechargeModel> healthRechargeModels;

    public List<GoActiveModel> getGoActiveModels() {
        return goActiveModels;
    }

    public void setGoActiveModels(List<GoActiveModel> goActiveModels) {
        this.goActiveModels = goActiveModels;
    }

    public List<HealthRechargeModel> getHealthRechargeModels() {
        return healthRechargeModels;
    }

    public void setHealthRechargeModels(List<HealthRechargeModel> healthRechargeModels) {
        this.healthRechargeModels = healthRechargeModels;
    }
}
