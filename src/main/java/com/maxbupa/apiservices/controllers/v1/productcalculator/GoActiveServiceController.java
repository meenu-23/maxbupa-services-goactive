package com.maxbupa.apiservices.controllers.v1.productcalculator;

import com.maxbupa.apiservices.model.productcalculator.goactive.GoActiveModel;
import com.maxbupa.apiservices.model.productcalculator.goactive.GoActiveResponse;
import com.maxbupa.apiservices.services.productcalculator.goactive.GoActiveService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/maxbupa/v1")
public class GoActiveServiceController {

    private GoActiveService goActiveService;

    public GoActiveServiceController(GoActiveService goActiveService) {
        this.goActiveService = goActiveService;
    }

    @PostMapping(value = "goActive")
    public GoActiveResponse calculateGoActivePremium(@RequestBody @Valid GoActiveModel goActiveModel)
    {
         return goActiveService.calculatePremium(goActiveModel);
    }
}
