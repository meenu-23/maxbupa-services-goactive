package com.maxbupa.apiservices.services.lead;

import com.maxbupa.apiservices.model.lead.FindYourPlanDTO;
import com.maxbupa.apiservices.model.lead.GetYourQuoteDTO;
import com.maxbupa.apiservices.model.lead.ReturningUserDTO;
import com.maxbupa.apiservices.model.leadupdate.FindYourPlanUpdateDTO;
import com.maxbupa.apiservices.model.leadupdate.GetYourQuoteUpdateDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LeadService {

    List<Object> identifyUser(String mcid);
    ReturningUserDTO insertNewUserWithoutContact(ReturningUserDTO returningUser);
    ResponseEntity<FindYourPlanDTO> insertFindYourPlan(FindYourPlanDTO findYourPlan);
    FindYourPlanUpdateDTO updateFindYourPlan(FindYourPlanUpdateDTO findYourPlan);
    ResponseEntity<GetYourQuoteDTO> insertGetYourQuote(GetYourQuoteDTO getYourQuote);
    GetYourQuoteUpdateDTO updateGetYourQuote(GetYourQuoteUpdateDTO getYourQuote);
    List<Object> getByActivityId(String personalizationId);

}
