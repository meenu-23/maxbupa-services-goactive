package com.maxbupa.apiservices.services.productcalculator.goactive;

import com.maxbupa.apiservices.model.productcalculator.goactive.GoActiveModel;
import com.maxbupa.apiservices.model.productcalculator.goactive.GoActiveResponse;

public interface GoActiveService {

    GoActiveResponse calculatePremium(GoActiveModel goActiveModel);
}
