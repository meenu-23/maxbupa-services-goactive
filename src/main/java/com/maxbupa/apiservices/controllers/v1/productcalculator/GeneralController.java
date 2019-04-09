package com.maxbupa.apiservices.controllers.v1.productcalculator;

import com.maxbupa.apiservices.model.productcalculator.generalcalculator.GeneralCalculator;
import com.maxbupa.apiservices.model.productcalculator.generalcalculator.GeneralResponse;
import com.maxbupa.apiservices.model.productcalculator.goactive.GoActiveResponse;
import com.maxbupa.apiservices.model.productcalculator.healthrecharge.HealthRechargeResponseThreeYears;
import com.maxbupa.apiservices.services.productcalculator.goactive.GoActiveService;
import com.maxbupa.apiservices.services.productcalculator.healthrecharge.HealthRechargeService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/maxbupa/v1")
public class GeneralController {

    private GoActiveService goActiveService;
    private HealthRechargeService healthRechargeService;

    public GeneralController(GoActiveService goActiveService, HealthRechargeService healthRechargeService) {
        this.goActiveService = goActiveService;
        this.healthRechargeService = healthRechargeService;
    }

    @PostMapping(value = "calculator")
    public GeneralResponse calculateGeneralCalculator(@RequestBody @Valid GeneralCalculator generalCalculator)
    {
        GeneralResponse generalResponse = new GeneralResponse();

        if(! generalCalculator.getGoActiveModels().isEmpty())
        {
            List<GoActiveResponse> goActiveResponses = new ArrayList<>();
            for(int i= 0 ; i <= generalCalculator.getGoActiveModels().size()-1 ; i++)
            {
                goActiveResponses.add(goActiveService.calculatePremium(generalCalculator.getGoActiveModels().get(i)));
            }
            generalResponse.setGoActiveResponseList(goActiveResponses);
        }

        if(! generalCalculator.getHealthRechargeModels().isEmpty() )
        {
            List<HealthRechargeResponseThreeYears> healthRechargeResponseThreeYears = new ArrayList<>();
            for(int i= 0 ; i <= generalCalculator.getHealthRechargeModels().size()-1 ; i++)
            {
                healthRechargeResponseThreeYears.add(healthRechargeService.calculatePremium(generalCalculator.getHealthRechargeModels().get(i)));
            }
            generalResponse.setHealthRechargeResponseThreeYears(healthRechargeResponseThreeYears);
        }
        return generalResponse;
    }
}