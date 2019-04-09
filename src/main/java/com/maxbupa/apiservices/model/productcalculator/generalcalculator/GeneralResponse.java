package com.maxbupa.apiservices.model.productcalculator.generalcalculator;

import com.maxbupa.apiservices.model.productcalculator.goactive.GoActiveResponse;
import com.maxbupa.apiservices.model.productcalculator.healthrecharge.HealthRechargeResponseThreeYears;

import java.util.List;

public class GeneralResponse {

    private List<GoActiveResponse> goActiveResponseList;
    private List<HealthRechargeResponseThreeYears> healthRechargeResponseThreeYears;

    public List<GoActiveResponse> getGoActiveResponseList() {
        return goActiveResponseList;
    }

    public void setGoActiveResponseList(List<GoActiveResponse> goActiveResponseList) {
        this.goActiveResponseList = goActiveResponseList;
    }

    public List<HealthRechargeResponseThreeYears> getHealthRechargeResponseThreeYears() {
        return healthRechargeResponseThreeYears;
    }

    public void setHealthRechargeResponseThreeYears(List<HealthRechargeResponseThreeYears> healthRechargeResponseThreeYears) {
        this.healthRechargeResponseThreeYears = healthRechargeResponseThreeYears;
    }
}
