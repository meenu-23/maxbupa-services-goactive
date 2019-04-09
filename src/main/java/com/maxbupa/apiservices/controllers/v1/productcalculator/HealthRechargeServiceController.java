package com.maxbupa.apiservices.controllers.v1.productcalculator;

import com.maxbupa.apiservices.model.productcalculator.healthrecharge.HealthRechargeModel;
import com.maxbupa.apiservices.model.productcalculator.healthrecharge.HealthRechargeResponseThreeYears;
import com.maxbupa.apiservices.services.productcalculator.healthrecharge.HealthRechargeService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/maxbupa/v1")
public class HealthRechargeServiceController {

    private HealthRechargeService healthRechargeService;

    public HealthRechargeServiceController(HealthRechargeService healthRechargeService) {
        this.healthRechargeService = healthRechargeService;
    }

    @PostMapping(value = "healthRechargePremium")
    public HealthRechargeResponseThreeYears calculateHealthRechargePremium(@RequestBody @Valid HealthRechargeModel healthRechargeModel) {
        return healthRechargeService.calculatePremium(healthRechargeModel);
    }
}
