package com.maxbupa.apiservices.controllers.v1.lead;

import com.maxbupa.apiservices.model.lead.FindYourPlanDTO;
import com.maxbupa.apiservices.model.lead.GetYourQuoteDTO;
import com.maxbupa.apiservices.model.lead.ReturningUserDTO;
import com.maxbupa.apiservices.model.leadupdate.FindYourPlanUpdateDTO;
import com.maxbupa.apiservices.model.leadupdate.GetYourQuoteUpdateDTO;
import com.maxbupa.apiservices.services.lead.LeadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


@RestController
@RequestMapping("/maxbupa/v1")
public class LeadServiceController {

    private final LeadService leadService;

    public LeadServiceController(LeadService leadService)
    {
        this.leadService = leadService;
    }

    //01.Based on mcid, Identify the type of user
    @GetMapping(value="identifyUserTypeByMcid/{mcid}")
    public List<Object> identifyUser(@PathVariable String mcid) {
        return leadService.identifyUser(mcid);
        }

    //02. Insert new user, if the user does not provide contact number
    @PostMapping(value = "newUsersWithoutContact")
    public ReturningUserDTO insertNewUserWithoutContact(@RequestBody ReturningUserDTO returningUser)
    {
        return leadService.insertNewUserWithoutContact(returningUser);
    }

    //03.Insert FindYourPlanFormDetails
    @PostMapping(value = "leads/findYourPlanFormDetails")
    public ResponseEntity<FindYourPlanDTO> insertFindYourPlan(@RequestBody FindYourPlanDTO findYourPlan)
    {
        return leadService.insertFindYourPlan(findYourPlan);
    }

    //04.Updates FindYourPlan form details for the record matching mcid, customerId, formId and personalizationId
    @PostMapping(value="leads/FindYourPlanForm")
    public FindYourPlanUpdateDTO updateFindYourPlan(@RequestBody @Valid FindYourPlanUpdateDTO findYourPlan) {
        return leadService.updateFindYourPlan(findYourPlan);
    }

    //05.Insert GetQuickQuoteFormDetails
    @PostMapping(value = "leads/getYourQuoteFormDetails")
    public ResponseEntity<GetYourQuoteDTO> insertGetYourQuote(@RequestBody GetYourQuoteDTO getYourQuote)
    {
        return leadService.insertGetYourQuote(getYourQuote);
    }

    //06.Updates GetYourQuote form details for the record matching mcid, customerId, formId and personalizationId
    @PostMapping(value="leads/GetYourQuoteForm")
    public GetYourQuoteUpdateDTO updateGetYourQuote(@RequestBody @Valid GetYourQuoteUpdateDTO getYourQuote) {
        return leadService.updateGetYourQuote(getYourQuote);
    }

    //07.Based on Personalization ID, Retrieve his Form Details
    @GetMapping(value="leads/activities/{personalizationId}")
    public List<Object> getByActivityId(@PathVariable String personalizationId){
        return leadService.getByActivityId(personalizationId);
    }
}