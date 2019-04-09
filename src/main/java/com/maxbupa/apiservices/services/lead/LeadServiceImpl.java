package com.maxbupa.apiservices.services.lead;

import com.maxbupa.apiservices.utilities.CommonUtility;
import com.maxbupa.apiservices.exception.BadRequestException;
import com.maxbupa.apiservices.exception.RecordNotFoundException;
import com.maxbupa.apiservices.model.lead.FindYourPlanDTO;
import com.maxbupa.apiservices.model.lead.GetYourQuoteDTO;
import com.maxbupa.apiservices.model.lead.ReturningUserDTO;
import com.maxbupa.apiservices.model.leadupdate.FindYourPlanUpdateDTO;
import com.maxbupa.apiservices.model.leadupdate.GetYourQuoteUpdateDTO;
import com.maxbupa.apiservices.model.prospect.Prospect;
import com.maxbupa.apiservices.model.response.UserStatus;
import com.mongodb.client.result.UpdateResult;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LeadServiceImpl implements LeadService {

    private static final String CUSTOMER_ID = "customerId";
    private static final String FORMS_DATE_TIME = "forms.dateTime";
    private static final String DEMO_LEAD = "DemoLead";
    private static final String FORM_ID_12345 = "12345";
    private static final String FORMS_PHONE_NUMBER ="forms.phoneNumber";
    private static final String FORMS_DOB = "forms.dob";
    private static final String GET_PERSONALIZATION_BY_ID_END_PONIT= "/maxbupa/getLeadDetailsByActivityId/{personalizationId}";
    private static final String INVALID_FORM_ID = "Invalid Form Id";
    private static final String FORMS_FROM_ID = "forms.formId";
    private static final String FORMS_PERSONALIZATION_ID = "forms.personalizationId";
    private static final String CONSISTENCY_SCORE = "consistencyScore";
    private static final String DISPOSITION_SCORE = "dispositionScore";
    private static final String LAST_VISITED_SITE = "lastVisitedSite";
    private static final String SOURCE_REFERRER = "sourceReferrer";
    private static final String SOURCE = "source";
    private static final String SUB_SOURCE = "subSource";
    private static final String LIFE_TIME_SOURCE = "lifeTimeSource";
    private static final String SUB_SEQUENT_SOURCES = "subsequentSources";
    private static final String LAST_PAGE_VISITED = "lastPageVisited";
    private static final String LOCATION = "location";
    private static final String BROWSER = "browser";
    private static final String OPERATING_SYSTEM = "operatingSystem";
    private static final String FORMS_BUYING_FOR = "forms.buyingFor";
    private static final String PRODUCTS_VIEWED = "productsViewed";
    private static final String FORMS_FORM_NAME = "forms.formName";
    private static final String FORMS_NAME = "forms.name";
    private static final String RECORDS_FOR = "Records for  ";
    private static final String FORM_ID = ", formId - ";
    private static final String PERSONALIZATION_ID = ", personalizationId - ";
    private static final String NOT_FOUND = "Not found";
    private static final String FORMS_QUOTE_ID = "forms.quoteId";
    private static final String FORMS_CITY = "forms.city";
    private static final String FORMS_NO_OF_ADULTS = "forms.noOfAdults";
    private static final String FORMS_NO_OF_CHILDS = "forms.noOfChildren";
    private static final String FORMS_PRODUCT_NAME = "forms.productName";
    private static final String FORMS_PLAN_TYPE = "forms.planType";
    private static final String FORMS_VARIANT = "forms.variant";
    private static final String FORMS_PAYABLE_PREMIUM = "forms.payablePremium";
    private static final String FORMS_ANNUAL_FAMILY_INCOME = "forms.annualFamilyIncome";
    private static final String FORMS_SUM_INSURED = "forms.sumInsured";
    private static final String FORMS_ID_09876 = "09876";

    private MongoTemplate mongoTemplate;

    @Autowired
    public LeadServiceImpl(MongoTemplate mongoTemplate)
    {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Object> identifyUser(String mcid) {
        UserStatus userStatus = new UserStatus();
        List<Object> formFlls = new ArrayList<>();

        //Lead
        Query qmcidwithDateLead = new Query();
        qmcidwithDateLead.addCriteria(Criteria.where("mcid").is(mcid).and(CUSTOMER_ID).ne(null));
        qmcidwithDateLead.with(new Sort(Sort.Direction.DESC,FORMS_DATE_TIME));
        JSONArray leadForms = new JSONArray(mongoTemplate.find(qmcidwithDateLead, Object.class, DEMO_LEAD));

        //Returning user
        Query qmcidwithDateRu = new Query();
        new Criteria();
        qmcidwithDateLead.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("mcid").is(mcid),
                        Criteria.where(CUSTOMER_ID).is(null)
                )
        );
        qmcidwithDateRu.addCriteria(Criteria.where("mcid").is(mcid));
        qmcidwithDateRu.with(new Sort(Sort.Direction.DESC,"dateTime"));
        List<Object> returningUser = mongoTemplate.find(qmcidwithDateRu, Object.class, DEMO_LEAD);


        //Adobe Analytics - Returning User
        Query qmcid = new Query();
        new Criteria();
        qmcid.addCriteria(Criteria.where("mcid").is(mcid));
        List<Prospect> adobeAnalytics = mongoTemplate.find(qmcid, Prospect.class, "AdobeAnalytics");


        if(adobeAnalytics.size()==0 && leadForms.length()==0 && returningUser.size()==0)
        {
            userStatus.setStatusCode(1);
            userStatus.setStatusDescription("NewUser");
            formFlls.add(userStatus);
            return formFlls;
        }
        else if(leadForms.length() > 0 )  //Lead has phone Numbers
        {
            userStatus.setStatusCode(2);
            userStatus.setStatusDescription("Lead");
            formFlls.add(userStatus);

            JSONObject obj = leadForms.getJSONObject(0);
            String customerId = obj.get(CUSTOMER_ID).toString().trim();
            List<Object> recentFormFlls = this.getByCustomerId(customerId);
            formFlls.add(recentFormFlls.get(0));

            return formFlls;
        }
        else if(returningUser.size() > 0 || adobeAnalytics.size() > 0)
        {
            userStatus.setStatusCode(3);
            userStatus.setStatusDescription("ReturningUser");
            formFlls.add(userStatus);
            formFlls.addAll(returningUser);
            return formFlls;
        }
        return formFlls;
    }

    @Override
    public ReturningUserDTO insertNewUserWithoutContact(ReturningUserDTO returningUser) {
        returningUser.setDateTime((CommonUtility.getCurrentDateTime()));
        returningUser.setCustomerId(null);
        return mongoTemplate.insert(returningUser, DEMO_LEAD);
    }

    @Override
    public ResponseEntity<FindYourPlanDTO> insertFindYourPlan(FindYourPlanDTO findYourPlan) {
        String customerId = "";
        String phoneNumber = findYourPlan.getForms().getPhoneNumber();
        Date dob = findYourPlan.getForms().getDob();
        if(findYourPlan.getFindYourPlanDetails().getFormId().equals(FORM_ID_12345))
        {
            if(phoneNumber!=null && dob!=null && !phoneNumber.isEmpty()) //case1- For Lead if the record exists, update record based on phone Number and DOB
            {
                //check all forms for phoneNumber and DOB and get the customerID
                Query q = new Query();
                q.addCriteria(
                        new Criteria().andOperator(
                                Criteria.where(FORMS_PHONE_NUMBER).is(phoneNumber),
                                Criteria.where(FORMS_DOB).is(dob)
                        )
                );
                q.with(new Sort(Sort.Direction.DESC, FORMS_DATE_TIME));
                JSONArray leadFormsWithCid = new JSONArray(mongoTemplate.find(q, Object.class, DEMO_LEAD));
                if(leadFormsWithCid.length() > 0) //existing customerid should be associated
                {
                    JSONObject obj = leadFormsWithCid.getJSONObject(0);
                    customerId = obj.get(CUSTOMER_ID).toString().trim();
                }
                else //generate new CustomerId, if the matching record does not exist for phoneNumber and dob in any of the form
                {
                    customerId = CommonUtility.getUUID();
                }
                findYourPlan.setCustomerId(customerId);
                findYourPlan.getFindYourPlanDetails().setDateTime(CommonUtility.getCurrentDateTime());
                findYourPlan.getFindYourPlanDetails().setPersonalizationId(CommonUtility.getUUID());
                FindYourPlanDTO savedForm = mongoTemplate.insert(findYourPlan,DEMO_LEAD);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path(GET_PERSONALIZATION_BY_ID_END_PONIT)
                        .buildAndExpand(savedForm.getFindYourPlanDetails().getPersonalizationId()).toUri();
                return ResponseEntity.created(location).body(savedForm);
            }
            else //CustomerId is not generated, since both dob and phoneNumber is not available. Generate personalisationID.
            {
                findYourPlan.getFindYourPlanDetails().setDateTime(CommonUtility.getCurrentDateTime());
                findYourPlan.getFindYourPlanDetails().setPersonalizationId(CommonUtility.getUUID());
                findYourPlan.setCustomerId(null);
                FindYourPlanDTO savedForm = mongoTemplate.insert(findYourPlan,DEMO_LEAD);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path(GET_PERSONALIZATION_BY_ID_END_PONIT)
                        .buildAndExpand(savedForm.getFindYourPlanDetails().getPersonalizationId()).toUri();
                return ResponseEntity.created(location).body(savedForm);
            }
        }
        else
        {
            throw new BadRequestException(INVALID_FORM_ID );
        }
    }

    @Override
    public FindYourPlanUpdateDTO updateFindYourPlan(FindYourPlanUpdateDTO findYourPlan) {
        String customerId = "";
        String phoneNumber = findYourPlan.getFindYourPlanDetails().getPhoneNumber();
        Date dob = findYourPlan.getFindYourPlanDetails().getDob();
        if (findYourPlan.getFindYourPlanDetails().getFormId().equals(FORM_ID_12345)) {
            findYourPlan.getFindYourPlanDetails().setDateTime((CommonUtility.getCurrentDateTime()));
            if ( (findYourPlan.getCustomerId()).isEmpty())  //case1 - Customer Id not yet generated
            {
                Query q = new Query();
                q.addCriteria(
                        new Criteria().andOperator(
                                Criteria.where("mcid").is(findYourPlan.getMcid()),
                                Criteria.where(FORMS_FROM_ID).is(findYourPlan.getFindYourPlanDetails().getFormId()),
                                Criteria.where(FORMS_PERSONALIZATION_ID).is(findYourPlan.getFindYourPlanDetails().getPersonalizationId())
                        )
                );
                if (phoneNumber != null &&  dob!= null && !phoneNumber.isEmpty()) { //if phonenumber,dob present, CustomerId is generated
                    Query qPhDb = new Query();
                    qPhDb.addCriteria(
                            new Criteria().andOperator(
                                    Criteria.where(FORMS_PHONE_NUMBER).is(findYourPlan.getFindYourPlanDetails().getPhoneNumber()),
                                    Criteria.where(FORMS_DOB).is(findYourPlan.getFindYourPlanDetails().getDob())
                            )
                    );
                    qPhDb.with(new Sort(Sort.Direction.DESC, FORMS_DATE_TIME));
                    JSONArray leadFormsWithCid = new JSONArray(mongoTemplate.find(qPhDb, Object.class, DEMO_LEAD));
                    if(leadFormsWithCid.length() > 0) //existing customerid should be associated
                    {
                        JSONObject obj = leadFormsWithCid.getJSONObject(0);
                        customerId = obj.get(CUSTOMER_ID).toString().trim();
                        findYourPlan.setCustomerId(customerId);
                    }
                    else    //generate new CustomerId, if the matching record does not exist for phoneNumber and dob in any of the form
                    {
                        customerId = CommonUtility.getUUID();
                        findYourPlan.setCustomerId(customerId);
                    }
                    Update update = new Update()
                            .set(CUSTOMER_ID, findYourPlan.getCustomerId())
                            .set(FORMS_DATE_TIME, findYourPlan.getFindYourPlanDetails().getDateTime())
                            .set(CONSISTENCY_SCORE, findYourPlan.getConsistencyScore())
                            .set(DISPOSITION_SCORE, findYourPlan.getDispositionScore())
                            .set(LAST_VISITED_SITE, findYourPlan.getLastVisitedSite())
                            .set(SOURCE_REFERRER, findYourPlan.getSourceReferrer())
                            .set(SOURCE, findYourPlan.getSource())
                            .set(SUB_SOURCE, findYourPlan.getSubSource())
                            .set(LIFE_TIME_SOURCE, findYourPlan.getLifeTimeSource())
                            .set(SUB_SEQUENT_SOURCES, findYourPlan.getSubsequentSources())
                            .set(LAST_PAGE_VISITED, findYourPlan.getLastPageVisited())
                            .set(LOCATION, findYourPlan.getLocation())
                            .set(BROWSER, findYourPlan.getBrowser())
                            .set(OPERATING_SYSTEM, findYourPlan.getOperatingSystem())
                            .set(FORMS_BUYING_FOR, findYourPlan.getFindYourPlanDetails().getBuyingFor())
                            .set(PRODUCTS_VIEWED, findYourPlan.getProductsViewed())
                            .set(FORMS_FORM_NAME, findYourPlan.getFindYourPlanDetails().getFormName())
                            .set(FORMS_NAME, findYourPlan.getFindYourPlanDetails().getName())
                            .set(FORMS_DOB, findYourPlan.getFindYourPlanDetails().getDob())
                            .set(FORMS_PHONE_NUMBER, findYourPlan.getFindYourPlanDetails().getPhoneNumber())
                            .set(FORMS_BUYING_FOR, findYourPlan.getFindYourPlanDetails().getBuyingFor());
                    UpdateResult res = mongoTemplate.updateFirst(q, update, FindYourPlanDTO.class, DEMO_LEAD);
                    if (res.getMatchedCount() == 0) {
                        throw new RecordNotFoundException(RECORDS_FOR
                                + FORM_ID + findYourPlan.getFindYourPlanDetails().getFormId()
                                + PERSONALIZATION_ID + findYourPlan.getFindYourPlanDetails().getPersonalizationId()
                                + " " + NOT_FOUND);
                    }
                    return findYourPlan;
                }
                else {  //if phonenumber,dob absent, CustomerId is not generated
                    findYourPlan.setCustomerId(null);
                    Update update = new Update().set(FORMS_NAME, findYourPlan.getFindYourPlanDetails().getName())
                            .set(CUSTOMER_ID, findYourPlan.getCustomerId())
                            .set(FORMS_DATE_TIME, findYourPlan.getFindYourPlanDetails().getDateTime())
                            .set(CONSISTENCY_SCORE, findYourPlan.getConsistencyScore())
                            .set(DISPOSITION_SCORE, findYourPlan.getDispositionScore())
                            .set(LAST_VISITED_SITE, findYourPlan.getLastVisitedSite())
                            .set(SOURCE_REFERRER, findYourPlan.getSourceReferrer())
                            .set(SOURCE, findYourPlan.getSource())
                            .set(SUB_SOURCE, findYourPlan.getSubSource())
                            .set(LIFE_TIME_SOURCE, findYourPlan.getLifeTimeSource())
                            .set(SUB_SEQUENT_SOURCES, findYourPlan.getSubsequentSources())
                            .set(LAST_PAGE_VISITED, findYourPlan.getLastPageVisited())
                            .set(LOCATION, findYourPlan.getLocation())
                            .set(BROWSER, findYourPlan.getBrowser())
                            .set(OPERATING_SYSTEM, findYourPlan.getOperatingSystem())
                            .set(FORMS_BUYING_FOR, findYourPlan.getFindYourPlanDetails().getBuyingFor())
                            .set(PRODUCTS_VIEWED, findYourPlan.getProductsViewed())
                            .set(FORMS_FORM_NAME, findYourPlan.getFindYourPlanDetails().getFormName())
                            .set(FORMS_NAME, findYourPlan.getFindYourPlanDetails().getName())
                            .set(FORMS_DOB, findYourPlan.getFindYourPlanDetails().getDob())
                            .set(FORMS_PHONE_NUMBER, findYourPlan.getFindYourPlanDetails().getPhoneNumber())
                            .set(FORMS_BUYING_FOR, findYourPlan.getFindYourPlanDetails().getBuyingFor());
                    UpdateResult res = mongoTemplate.updateFirst(q, update, FindYourPlanDTO.class, DEMO_LEAD);
                    if (res.getMatchedCount() == 0) {
                        throw new RecordNotFoundException(RECORDS_FOR
                                + FORM_ID + findYourPlan.getFindYourPlanDetails().getFormId()
                                + PERSONALIZATION_ID + findYourPlan.getFindYourPlanDetails().getPersonalizationId()
                                + " " + NOT_FOUND);
                    }
                }
                return findYourPlan;
            }
            else
            {   //CustomerID found update
                findYourPlan.getFindYourPlanDetails().setDateTime((CommonUtility.getCurrentDateTime()));
                Query qid = new Query();
                qid.addCriteria(
                        new Criteria().andOperator(
                                Criteria.where("mcid").is(findYourPlan.getMcid()),
                                Criteria.where(CUSTOMER_ID).is(findYourPlan.getCustomerId()),
                                Criteria.where(FORMS_FROM_ID).is(findYourPlan.getFindYourPlanDetails().getFormId()),
                                Criteria.where(FORMS_PERSONALIZATION_ID).is(findYourPlan.getFindYourPlanDetails().getPersonalizationId())
                        )
                );
                qid.with(new Sort(Sort.Direction.DESC,FORMS_DATE_TIME));
                Update update = new Update().set(FORMS_NAME, findYourPlan.getFindYourPlanDetails().getName())
                        .set(CUSTOMER_ID, findYourPlan.getCustomerId())
                        .set(FORMS_DATE_TIME, findYourPlan.getFindYourPlanDetails().getDateTime())
                        .set(CONSISTENCY_SCORE, findYourPlan.getConsistencyScore())
                        .set(DISPOSITION_SCORE, findYourPlan.getDispositionScore())
                        .set(LAST_VISITED_SITE, findYourPlan.getLastVisitedSite())
                        .set(SOURCE_REFERRER, findYourPlan.getSourceReferrer())
                        .set(SOURCE, findYourPlan.getSource())
                        .set(SUB_SOURCE, findYourPlan.getSubSource())
                        .set(LIFE_TIME_SOURCE, findYourPlan.getLifeTimeSource())
                        .set(SUB_SEQUENT_SOURCES, findYourPlan.getSubsequentSources())
                        .set(LAST_PAGE_VISITED, findYourPlan.getLastPageVisited())
                        .set(LOCATION, findYourPlan.getLocation())
                        .set(BROWSER, findYourPlan.getBrowser())
                        .set(OPERATING_SYSTEM, findYourPlan.getOperatingSystem())
                        .set(FORMS_BUYING_FOR, findYourPlan.getFindYourPlanDetails().getBuyingFor())
                        .set(PRODUCTS_VIEWED, findYourPlan.getProductsViewed())
                        .set(FORMS_FORM_NAME, findYourPlan.getFindYourPlanDetails().getFormName())
                        .set(FORMS_NAME, findYourPlan.getFindYourPlanDetails().getName())
                        .set(FORMS_BUYING_FOR, findYourPlan.getFindYourPlanDetails().getBuyingFor());
                UpdateResult res = mongoTemplate.updateFirst(qid, update, FindYourPlanDTO.class, DEMO_LEAD);
                if (res.getMatchedCount() == 0) {
                    throw new RecordNotFoundException(RECORDS_FOR
                            + FORM_ID + findYourPlan.getFindYourPlanDetails().getFormId()
                            + PERSONALIZATION_ID + findYourPlan.getFindYourPlanDetails().getPersonalizationId()
                            + " " + NOT_FOUND);
                }
                return findYourPlan;
            }
        }

        else
        {
            throw new BadRequestException(INVALID_FORM_ID );
        }
    }

    @Override
    public ResponseEntity<GetYourQuoteDTO> insertGetYourQuote(GetYourQuoteDTO getYourQuote) {
        String customerId = "";
        String phoneNumber = getYourQuote.getForms().getPhoneNumber();
        Date dob = getYourQuote.getForms().getDob();
        if(getYourQuote.getGetYourQuoteFormDetails().getFormId().equals(FORMS_ID_09876))
        {
            if(phoneNumber!=null && !phoneNumber.isEmpty() && dob!=null) //case1- For Lead if the record exists, update record based on phone Number and DOB
            {   //check all forms for phoneNumber and DOB and get the customerID
                Query q = new Query();
                q.addCriteria(
                        new Criteria().andOperator(
                                Criteria.where(FORMS_PHONE_NUMBER).is(phoneNumber),
                                Criteria.where(FORMS_DOB).is(dob)
                        )
                );
                q.with(new Sort(Sort.Direction.DESC, FORMS_DATE_TIME));
                JSONArray leadFormsWithCid = new JSONArray(mongoTemplate.find(q, Object.class, DEMO_LEAD));
                if(leadFormsWithCid.length() > 0) //existing customerid should be associated
                {
                    JSONObject obj = leadFormsWithCid.getJSONObject(0);
                    customerId = obj.get(CUSTOMER_ID).toString().trim();
                }
                else    //generate new CustomerId, if the matching record does not exist for phoneNumber and dob in any of the form
                {
                    customerId = CommonUtility.getUUID();
                }
                if(getYourQuote.getGetYourQuoteFormDetails().getPayablePremium()!=null && !(getYourQuote.getGetYourQuoteFormDetails().getPayablePremium()).isEmpty()) //generate QuoteId if payable premium is not null
                {
                    getYourQuote.getGetYourQuoteFormDetails().setQuoteId(CommonUtility.getUUID());
                }
                else    //if payable premium is null, set QuoteId to null
                {
                    getYourQuote.getGetYourQuoteFormDetails().setQuoteId(null);
                }
                getYourQuote.setCustomerId(customerId);
                getYourQuote.getGetYourQuoteFormDetails().setDateTime(CommonUtility.getCurrentDateTime());
                getYourQuote.getGetYourQuoteFormDetails().setPersonalizationId(CommonUtility.getUUID());
                GetYourQuoteDTO savedForm = mongoTemplate.insert(getYourQuote, DEMO_LEAD);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path(GET_PERSONALIZATION_BY_ID_END_PONIT)
                        .buildAndExpand(savedForm.getGetYourQuoteFormDetails().getPersonalizationId()).toUri();
                return ResponseEntity.created(location).body(savedForm);
            }
            else    //CustomerId is not generated, since both dob and phoneNumber is not available. Generate personalisationID.
            {
                getYourQuote.getGetYourQuoteFormDetails().setDateTime(CommonUtility.getCurrentDateTime());
                getYourQuote.getGetYourQuoteFormDetails().setPersonalizationId(CommonUtility.getUUID());
                getYourQuote.setCustomerId(null);   //set CustomerId to null
                getYourQuote.getGetYourQuoteFormDetails().setQuoteId(null);     //set QuoteId to null
                GetYourQuoteDTO savedForm = mongoTemplate.insert(getYourQuote, DEMO_LEAD);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path(GET_PERSONALIZATION_BY_ID_END_PONIT)
                        .buildAndExpand(savedForm.getGetYourQuoteFormDetails().getPersonalizationId()).toUri();
                return ResponseEntity.created(location).body(savedForm);
            }
        }
        else
        {
            throw new BadRequestException(INVALID_FORM_ID );
        }
    }

    @Override
    public GetYourQuoteUpdateDTO updateGetYourQuote(GetYourQuoteUpdateDTO getYourQuote) {
        String customerId = "";
        Date dob = getYourQuote.getGetYourQuoteFormDetails().getDob();
        String phoneNumber = getYourQuote.getGetYourQuoteFormDetails().getPhoneNumber();

        if (getYourQuote.getGetYourQuoteFormDetails().getFormId().equals(FORMS_ID_09876)) {
            getYourQuote.getGetYourQuoteFormDetails().setDateTime((CommonUtility.getCurrentDateTime()));
            if ((getYourQuote.getCustomerId()).isEmpty())   //case1 - Customer Id not yet generated
            {
                Query q = new Query();
                q.addCriteria(
                        new Criteria().andOperator(
                                Criteria.where("mcid").is(getYourQuote.getMcid()),
                                Criteria.where(FORMS_FROM_ID).is(getYourQuote.getGetYourQuoteFormDetails().getFormId()),
                                Criteria.where(FORMS_PERSONALIZATION_ID).is(getYourQuote.getGetYourQuoteFormDetails().getPersonalizationId())
                        )
                );
                if (phoneNumber != null && dob != null && !phoneNumber.isEmpty()) { //if phonenumber,dob present, CustomerId is generated

                    Query qPhDb = new Query();
                    qPhDb.addCriteria(
                            new Criteria().andOperator(
                                    Criteria.where(FORMS_PHONE_NUMBER).is(getYourQuote.getGetYourQuoteFormDetails().getPhoneNumber()),
                                    Criteria.where(FORMS_DOB).is(getYourQuote.getGetYourQuoteFormDetails().getDob())
                            )
                    );
                    qPhDb.with(new Sort(Sort.Direction.DESC, FORMS_DATE_TIME));
                    JSONArray leadFormsWithCid = new JSONArray(mongoTemplate.find(qPhDb, Object.class, DEMO_LEAD));
                    if(leadFormsWithCid.length() > 0)   //existing customerid should be associated
                    {
                        JSONObject obj = leadFormsWithCid.getJSONObject(0);
                        customerId = obj.get(CUSTOMER_ID).toString().trim();
                        getYourQuote.setCustomerId(customerId);
                    }
                    else    //generate new CustomerId, if the matching record does not exist for phoneNumber and dob in any of the form
                    {
                        customerId = CommonUtility.getUUID();
                        getYourQuote.setCustomerId(customerId);
                    }
                    if(getYourQuote.getGetYourQuoteFormDetails().getPayablePremium()!=null && !(getYourQuote.getGetYourQuoteFormDetails().getPayablePremium()).isEmpty()) //generate QuoteId if payable premium is not null
                    {
                        getYourQuote.getGetYourQuoteFormDetails().setQuoteId(CommonUtility.getUUID());
                    }
                    else // if payable premium is null, set QuoteId to null
                    {
                        getYourQuote.getGetYourQuoteFormDetails().setQuoteId(null);
                    }
                    Update update = new Update()
                            .set(CUSTOMER_ID, getYourQuote.getCustomerId())
                            .set(FORMS_QUOTE_ID, getYourQuote.getGetYourQuoteFormDetails().getQuoteId())
                            .set(FORMS_DATE_TIME, getYourQuote.getGetYourQuoteFormDetails().getDateTime())
                            .set(CONSISTENCY_SCORE, getYourQuote.getConsistencyScore())
                            .set(DISPOSITION_SCORE, getYourQuote.getDispositionScore())
                            .set(LAST_VISITED_SITE, getYourQuote.getLastVisitedSite())
                            .set(SOURCE_REFERRER, getYourQuote.getSourceReferrer())
                            .set(SOURCE, getYourQuote.getSource())
                            .set(SUB_SOURCE, getYourQuote.getSubSource())
                            .set(LIFE_TIME_SOURCE, getYourQuote.getLifeTimeSource())
                            .set(SUB_SEQUENT_SOURCES, getYourQuote.getSubsequentSources())
                            .set(LAST_PAGE_VISITED, getYourQuote.getLastPageVisited())
                            .set(LOCATION, getYourQuote.getLocation())
                            .set(BROWSER, getYourQuote.getBrowser())
                            .set(OPERATING_SYSTEM, getYourQuote.getOperatingSystem())
                            .set(PRODUCTS_VIEWED, getYourQuote.getProductsViewed())
                            .set(FORMS_FORM_NAME,getYourQuote.getGetYourQuoteFormDetails().getFormName())
                            .set(FORMS_NAME, getYourQuote.getGetYourQuoteFormDetails().getName())
                            .set(FORMS_DOB, getYourQuote.getGetYourQuoteFormDetails().getDob())
                            .set(FORMS_PHONE_NUMBER, getYourQuote.getGetYourQuoteFormDetails().getPhoneNumber())
                            .set(FORMS_CITY, getYourQuote.getGetYourQuoteFormDetails().getCity())
                            .set(FORMS_NO_OF_ADULTS, getYourQuote.getGetYourQuoteFormDetails().getNoOfAdults())
                            .set(FORMS_NO_OF_CHILDS, getYourQuote.getGetYourQuoteFormDetails().getNoOfChildren())
                            .set(FORMS_PRODUCT_NAME, getYourQuote.getGetYourQuoteFormDetails().getProductName())
                            .set(FORMS_PLAN_TYPE, getYourQuote.getGetYourQuoteFormDetails().getPlanType())
                            .set(FORMS_VARIANT, getYourQuote.getGetYourQuoteFormDetails().getVariant())
                            .set(FORMS_PAYABLE_PREMIUM, getYourQuote.getGetYourQuoteFormDetails().getPayablePremium())
                            .set(FORMS_ANNUAL_FAMILY_INCOME, getYourQuote.getGetYourQuoteFormDetails().getAnnualFamilyIncome())
                            .set(FORMS_SUM_INSURED, getYourQuote.getGetYourQuoteFormDetails().getSumInsured());
                    UpdateResult res = mongoTemplate.updateFirst(q, update, GetYourQuoteDTO.class, DEMO_LEAD);
                    if (res.getMatchedCount() == 0) {
                        throw new RecordNotFoundException(RECORDS_FOR
                                + FORM_ID + getYourQuote.getGetYourQuoteFormDetails().getFormId()
                                + PERSONALIZATION_ID + getYourQuote.getGetYourQuoteFormDetails().getPersonalizationId()
                                + " " + NOT_FOUND);
                    }
                    return getYourQuote;
                }
                else {      //if phonenumber,dob absent, CustomerId is not generated
                    getYourQuote.setCustomerId(null);
                    getYourQuote.getGetYourQuoteFormDetails().setQuoteId(null);
                    Update update = new Update()
                            .set(CUSTOMER_ID, getYourQuote.getCustomerId())
                            .set(FORMS_QUOTE_ID, getYourQuote.getGetYourQuoteFormDetails().getQuoteId())
                            .set(FORMS_DATE_TIME, getYourQuote.getGetYourQuoteFormDetails().getDateTime())
                            .set(CONSISTENCY_SCORE, getYourQuote.getConsistencyScore())
                            .set(DISPOSITION_SCORE, getYourQuote.getDispositionScore())
                            .set(LAST_VISITED_SITE, getYourQuote.getLastVisitedSite())
                            .set(SOURCE_REFERRER, getYourQuote.getSourceReferrer())
                            .set(SOURCE, getYourQuote.getSource())
                            .set(SUB_SOURCE, getYourQuote.getSubSource())
                            .set(LIFE_TIME_SOURCE, getYourQuote.getLifeTimeSource())
                            .set(SUB_SEQUENT_SOURCES, getYourQuote.getSubsequentSources())
                            .set(LAST_PAGE_VISITED, getYourQuote.getLastPageVisited())
                            .set(LOCATION, getYourQuote.getLocation())
                            .set(BROWSER, getYourQuote.getBrowser())
                            .set(OPERATING_SYSTEM, getYourQuote.getOperatingSystem())
                            .set(PRODUCTS_VIEWED, getYourQuote.getProductsViewed())
                            .set(FORMS_FORM_NAME,getYourQuote.getGetYourQuoteFormDetails().getFormName())
                            .set(FORMS_NAME, getYourQuote.getGetYourQuoteFormDetails().getName())
                            .set(FORMS_DOB, getYourQuote.getGetYourQuoteFormDetails().getDob())
                            .set(FORMS_PHONE_NUMBER, getYourQuote.getGetYourQuoteFormDetails().getPhoneNumber())
                            .set(FORMS_CITY, getYourQuote.getGetYourQuoteFormDetails().getCity())
                            .set(FORMS_NO_OF_ADULTS, getYourQuote.getGetYourQuoteFormDetails().getNoOfAdults())
                            .set(FORMS_NO_OF_CHILDS, getYourQuote.getGetYourQuoteFormDetails().getNoOfChildren())
                            .set(FORMS_PRODUCT_NAME, getYourQuote.getGetYourQuoteFormDetails().getProductName())
                            .set(FORMS_PLAN_TYPE, getYourQuote.getGetYourQuoteFormDetails().getPlanType())
                            .set(FORMS_VARIANT, getYourQuote.getGetYourQuoteFormDetails().getVariant())
                            .set(FORMS_PAYABLE_PREMIUM, getYourQuote.getGetYourQuoteFormDetails().getPayablePremium())
                            .set(FORMS_ANNUAL_FAMILY_INCOME, getYourQuote.getGetYourQuoteFormDetails().getAnnualFamilyIncome())
                            .set(FORMS_SUM_INSURED, getYourQuote.getGetYourQuoteFormDetails().getSumInsured());
                    UpdateResult res = mongoTemplate.updateFirst(q, update, FindYourPlanDTO.class, DEMO_LEAD);
                    if (res.getMatchedCount() == 0) {
                        throw new RecordNotFoundException(RECORDS_FOR
                                + FORM_ID + getYourQuote.getGetYourQuoteFormDetails().getFormId()
                                + PERSONALIZATION_ID + getYourQuote.getGetYourQuoteFormDetails().getPersonalizationId()
                                + " " + NOT_FOUND);
                    }
                }
                return getYourQuote;
            }
            else
            {   //CustomerID found update
                getYourQuote.getGetYourQuoteFormDetails().setDateTime((CommonUtility.getCurrentDateTime()));
                Query qid = new Query();
                qid.addCriteria(
                        new Criteria().andOperator(
                                Criteria.where("mcid").is(getYourQuote.getMcid()),
                                Criteria.where(CUSTOMER_ID).is(getYourQuote.getCustomerId()),
                                Criteria.where(FORMS_FROM_ID).is(getYourQuote.getGetYourQuoteFormDetails().getFormId()),
                                Criteria.where(FORMS_PERSONALIZATION_ID).is(getYourQuote.getGetYourQuoteFormDetails().getPersonalizationId())
                        )
                );
                qid.with(new Sort(Sort.Direction.DESC,FORMS_DATE_TIME));
                if(getYourQuote.getGetYourQuoteFormDetails().getPayablePremium()!=null && !(getYourQuote.getGetYourQuoteFormDetails().getPayablePremium()).isEmpty()) //generate QuoteId if payable premium is not null
                {
                    getYourQuote.getGetYourQuoteFormDetails().setQuoteId(CommonUtility.getUUID());
                }
                else    // if payable premium is null, set QuoteId to null
                {
                    getYourQuote.getGetYourQuoteFormDetails().setQuoteId(null);
                }
                Update update = new Update()
                        .set(CUSTOMER_ID, getYourQuote.getCustomerId())
                        .set(FORMS_QUOTE_ID, getYourQuote.getGetYourQuoteFormDetails().getQuoteId())
                        .set(FORMS_DATE_TIME, getYourQuote.getGetYourQuoteFormDetails().getDateTime())
                        .set(CONSISTENCY_SCORE, getYourQuote.getConsistencyScore())
                        .set(DISPOSITION_SCORE, getYourQuote.getDispositionScore())
                        .set(LAST_VISITED_SITE, getYourQuote.getLastVisitedSite())
                        .set(SOURCE_REFERRER, getYourQuote.getSourceReferrer())
                        .set(SOURCE, getYourQuote.getSource())
                        .set(SUB_SOURCE, getYourQuote.getSubSource())
                        .set(LIFE_TIME_SOURCE, getYourQuote.getLifeTimeSource())
                        .set(SUB_SEQUENT_SOURCES, getYourQuote.getSubsequentSources())
                        .set(LAST_PAGE_VISITED, getYourQuote.getLastPageVisited())
                        .set(LOCATION, getYourQuote.getLocation())
                        .set(BROWSER, getYourQuote.getBrowser())
                        .set(OPERATING_SYSTEM, getYourQuote.getOperatingSystem())
                        .set(PRODUCTS_VIEWED, getYourQuote.getProductsViewed())
                        .set(FORMS_FORM_NAME,getYourQuote.getGetYourQuoteFormDetails().getFormName())
                        .set(FORMS_NAME, getYourQuote.getGetYourQuoteFormDetails().getName())
                        .set(FORMS_DOB, getYourQuote.getGetYourQuoteFormDetails().getDob())
                        .set(FORMS_PHONE_NUMBER, getYourQuote.getGetYourQuoteFormDetails().getPhoneNumber())
                        .set(FORMS_CITY, getYourQuote.getGetYourQuoteFormDetails().getCity())
                        .set(FORMS_NO_OF_ADULTS, getYourQuote.getGetYourQuoteFormDetails().getNoOfAdults())
                        .set(FORMS_NO_OF_CHILDS, getYourQuote.getGetYourQuoteFormDetails().getNoOfChildren())
                        .set(FORMS_PRODUCT_NAME, getYourQuote.getGetYourQuoteFormDetails().getProductName())
                        .set(FORMS_PLAN_TYPE, getYourQuote.getGetYourQuoteFormDetails().getPlanType())
                        .set(FORMS_VARIANT, getYourQuote.getGetYourQuoteFormDetails().getVariant())
                        .set(FORMS_PAYABLE_PREMIUM, getYourQuote.getGetYourQuoteFormDetails().getPayablePremium())
                        .set(FORMS_ANNUAL_FAMILY_INCOME, getYourQuote.getGetYourQuoteFormDetails().getAnnualFamilyIncome())
                        .set(FORMS_SUM_INSURED, getYourQuote.getGetYourQuoteFormDetails().getSumInsured());
                UpdateResult res = mongoTemplate.updateFirst(qid, update, FindYourPlanDTO.class, DEMO_LEAD);
                if (res.getMatchedCount() == 0) {
                    throw new RecordNotFoundException(RECORDS_FOR
                            + FORM_ID + getYourQuote.getGetYourQuoteFormDetails().getFormId()
                            + PERSONALIZATION_ID + getYourQuote.getGetYourQuoteFormDetails().getPersonalizationId()
                            + " " + NOT_FOUND);
                }
                return getYourQuote;
            }
        }

        else
        {
            throw new BadRequestException(INVALID_FORM_ID );
        }
    }

    @Override
    public List<Object> getByActivityId(String personalizationId) {
        Query q = new Query();
        new Criteria();
        q.addCriteria(Criteria.where(FORMS_PERSONALIZATION_ID).is(personalizationId));
        if(mongoTemplate.find(q, Object.class, DEMO_LEAD).isEmpty())
        {
            throw new RecordNotFoundException("Records for personalizationId -" + personalizationId + " " + NOT_FOUND);
        }
        return mongoTemplate.find(q, Object.class, DEMO_LEAD);

    }

    public List<Object> getByCustomerIdAndFormId(String customerId)
    {
        List<Object> formFlls = new ArrayList<>();

        Query qCucoFP = new Query();
        new Criteria();
        qCucoFP.addCriteria(Criteria.where(CUSTOMER_ID).is(customerId).and(FORMS_FROM_ID).is(FORM_ID_12345));
        qCucoFP.with(new Sort(Sort.Direction.DESC,FORMS_DATE_TIME));
        List<Object> leadFormsCucoFP = mongoTemplate.find(qCucoFP, Object.class, DEMO_LEAD);

        Query qCucoGQ = new Query();
        new Criteria();
        qCucoGQ.addCriteria(Criteria.where(CUSTOMER_ID).is(customerId).and(FORMS_FROM_ID).is(FORMS_ID_09876));
        qCucoGQ.with(new Sort(Sort.Direction.DESC,FORMS_DATE_TIME));
        List<Object> leadFormsCucoGQ = mongoTemplate.find(qCucoGQ, Object.class, DEMO_LEAD);

        formFlls.add(leadFormsCucoFP.get(0));
        formFlls.add(leadFormsCucoGQ.get(0));

        return formFlls;

    }

    public List<Object> getByCustomerId(String customerId)
    {
        List<Object> formFlls = new ArrayList<>();
        Query qCuco = new Query();
        new Criteria();
        qCuco.addCriteria(Criteria.where(CUSTOMER_ID).is(customerId));
        qCuco.with(new Sort(Sort.Direction.DESC,FORMS_DATE_TIME));
        List<Object> leadFormsCucoFP = mongoTemplate.find(qCuco, Object.class, DEMO_LEAD);
        formFlls.add(leadFormsCucoFP.get(0));
        return formFlls;
    }
}