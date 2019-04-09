package com.maxbupa.apiservices.controllers.v1.planidgeneration;

import com.google.common.base.Strings;
import com.maxbupa.apiservices.model.planidgeneration.PlanQueryParameters;
import com.maxbupa.apiservices.model.product.request.FilePath;
import com.maxbupa.apiservices.services.planidgeneration.PlanIdServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/maxbupa")
public class PlanIdServiceController {

    private MongoTemplate mongoTemplate;

    @Autowired
    public PlanIdServiceController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @PostMapping(value = "/insertPlanIds")
    public void insertPlanIds(@RequestBody FilePath filePath) {
        List<String> planIds = PlanIdServiceImpl.readPlanIds(filePath.getFilePathOfProduct());
        mongoTemplate.insert(planIds, "plandetails");
    }

    @PostMapping(value = "/getPlanId")
    public Object fetchPlanId(@RequestBody PlanQueryParameters planQueryParameters) {
        Query fetchPlanQuery = new Query();
        Criteria criteria = new Criteria();

        criteria.and("PRODUCT_NAME").is(planQueryParameters.getProductType());

        if (!Strings.isNullOrEmpty(planQueryParameters.getNoOfAdults())) {
            criteria.and("ADULT_COVERED").is(planQueryParameters.getNoOfAdults());
        }

        if (!Strings.isNullOrEmpty(planQueryParameters.getNoOfChild())) {
            criteria.and("CHILD_COVERED").is(planQueryParameters.getNoOfChild());
        }

        if (!Strings.isNullOrEmpty(planQueryParameters.getSumInsured())) {
            criteria.and("PLAN_LIMIT").is(planQueryParameters.getSumInsured());
        }

        if (!Strings.isNullOrEmpty(planQueryParameters.getZone())) {
            criteria.and("ZONE").is(planQueryParameters.getZone());
        }

        if (!Strings.isNullOrEmpty(planQueryParameters.getPaCover())) {
            criteria.and("PA_COVERED").is(planQueryParameters.getPaCover());
        }

        if (!Strings.isNullOrEmpty(planQueryParameters.getPaLimit())) {
            criteria.and("PA_LIMIT").is(planQueryParameters.getPaLimit());
        }

        if (!Strings.isNullOrEmpty(planQueryParameters.getDeductableAmount())) {
            criteria.and("DEDUCT_AMOUNT").is(planQueryParameters.getDeductableAmount());
        }

        if (!Strings.isNullOrEmpty(planQueryParameters.getHospitalCash())) {
            criteria.and("HOSPITAL_CASH").is(planQueryParameters.getHospitalCash());
        }

        if (!Strings.isNullOrEmpty(planQueryParameters.getCoPayment())) {
            criteria.and("CO_PAY").is(planQueryParameters.getCoPayment());
        }

        if (!Strings.isNullOrEmpty(planQueryParameters.getInternationalCoverage())) {
            criteria.and("INCLUDING_USA_CANADA").is(planQueryParameters.getInternationalCoverage());
        }

        if (!Strings.isNullOrEmpty(planQueryParameters.getRoomProvided())) {
            criteria.and("ROOM_MODIFIED_FLAG").is(planQueryParameters.getRoomProvided());
        }

        fetchPlanQuery.addCriteria(criteria);
        return mongoTemplate.find(fetchPlanQuery, Object.class, "plandetails");

    }

}