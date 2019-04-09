package com.maxbupa.apiservices.services.productcalculator.healthrecharge;


import com.maxbupa.apiservices.model.productcalculator.healthrecharge.HealthRechargeModel;
import com.maxbupa.apiservices.model.productcalculator.healthrecharge.HealthRechargeResponseThreeYears;

public interface HealthRechargeService {

    HealthRechargeResponseThreeYears calculatePremium(HealthRechargeModel healthRechargeModel);
}
