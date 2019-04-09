package com.maxbupa.apiservices.services.productcalculator.goactive;

import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.maxbupa.apiservices.exception.RecordNotFoundException;
import com.maxbupa.apiservices.model.productcalculator.goactive.GoActiveModel;
import com.maxbupa.apiservices.model.productcalculator.goactive.GoActiveResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class GoActiveFetch extends GoActiveServiceImpl{

    private MongoTemplate mongoTemplate;
    private static final int MAX_AGE = 65;
    private static final String PRODUCT_ID = "ProductId";
    private static final String MAXBUPA_PRODUCTS = "maxbupaProducts";
    private static final String PRICE = "price";
    private static final String RECORD_NOT_FOUND_EXCEPTION = "Record Not Found";
    private static final String INVALID_AGE = "Age Should Not Exceed 65";
    private static final String PERCENTAGE_VALUE = "PercentageValue";
    private static final String FOR_PRODUCTID = " For ProductId ";
    private String productId;
    private static final String TYPE = "Type";
    private static final String TYPE_DISCOUNT = "Discount";
    private static final String TYPE_PA = "PA";
    private static final String TYPE_DEDUCTIBLE = "Deductible";
    private static final String TYPE_HEALTHCOACH = "HealthCoach";
    private JSONArray discountDoc;
    private JSONArray personalAccidentDoc;
    private JSONArray deductibleDoc;
    private JSONArray healthCoachDoc;
    private JSONArray basePremiumDoc;

    @Autowired
    public GoActiveFetch(MongoTemplate mongoTemplate)
    {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public GoActiveResponse calculatePremium(GoActiveModel goActiveModel) {
        fetchValues(goActiveModel);
        return calPremium();
    }

    private void fetchLoadingfactor() {
        Query optionalRateDoc = new Query();
        optionalRateDoc.fields().exclude("_id");
        optionalRateDoc.addCriteria(
                new Criteria().andOperator(
                        Criteria.where(PRODUCT_ID).is(productId),
                        Criteria.where(TYPE).exists(true)
                )
        );
        JSONArray loading = new JSONArray(mongoTemplate.find(optionalRateDoc, Object.class, MAXBUPA_PRODUCTS));
        if (loading.length()!=0) {
            for (int noOfDocInDb = 0; noOfDocInDb < loading.length(); noOfDocInDb++) {

                switch (loading.getJSONObject(noOfDocInDb).get("Type").toString()) {
                    case TYPE_DISCOUNT:
                        JSONArray discountDocument = new JSONArray();
                        discountDocument.put(loading.getJSONObject(noOfDocInDb));
                        discountDoc = discountDocument;
                        break;
                    case TYPE_PA:
                        JSONArray personalAccidentDocument = new JSONArray();
                        personalAccidentDocument.put(loading.getJSONObject(noOfDocInDb));
                        personalAccidentDoc = personalAccidentDocument;
                        break;
                    case TYPE_DEDUCTIBLE:
                        JSONArray deductibleDocument = new JSONArray();
                        deductibleDocument.put(loading.getJSONObject(noOfDocInDb));
                        deductibleDoc = deductibleDocument;
                        break;
                    case TYPE_HEALTHCOACH:
                        JSONArray healthCoachDocument = new JSONArray();
                        healthCoachDocument.put(loading.getJSONObject(noOfDocInDb));
                        healthCoachDoc = healthCoachDocument;
                        break;
                    default:
                        break;
                }
            }
        }
    }


    private void fetchValues(GoActiveModel goActiveModel)
    {
        String discountType = goActiveModel.getDiscount().trim();
        setDiscountType(discountType);
        productId = goActiveModel.getProductId().trim();
        setProductId(productId);
        setComponentId(goActiveModel.getComponentId().trim());
        String ageOfEldestMember = goActiveModel.getAgeOfEldestMember().trim();
        String ageOfSecondMember = goActiveModel.getAgeOfSecondMember().trim();
        if (Integer.parseInt(ageOfEldestMember) > MAX_AGE || Integer.parseInt(ageOfSecondMember) > MAX_AGE ) {
            throw new RecordNotFoundException(INVALID_AGE + FOR_PRODUCTID + productId);
        } else {
            fetchLoadingfactor();
            fetchTheRequiredGoActiveValues(goActiveModel);
        }
    }

    private void fetchTheRequiredGoActiveValues(GoActiveModel goActiveModel)
    {
        String ageOfEldestMember = goActiveModel.getAgeOfEldestMember().trim();
        String sumInsured = goActiveModel.getSumInsured().trim();
        String deductible = goActiveModel.getDeductible().trim();
        String paCover = goActiveModel.getPersonalAccidentOptionalCover().trim();
        String paSi = goActiveModel.getPersonalAccidentSumInsured().trim();
        String discount = goActiveModel.getDiscount().trim();
        String healthCoach = goActiveModel.getHealthCoach().trim();
        fetchBasePremiumDocument(goActiveModel);
        findPremiumfOldestMember(ageOfEldestMember,sumInsured);
        if(deductible.equals("No"))
        {
            setDeductibleMultiplicativeFactor("100");
        }
        else
        {
            findDeductible(sumInsured,deductible);
        }
        if(goActiveModel.getAdvantageDiscountFlag().trim().equals("Yes") && Integer.parseInt(ageOfEldestMember)<=35)
        {
            findAdvantageDiscount();
        }
        else
        {
            setAdvantageDiscount("0");
        }
        if(goActiveModel.getPersonalAccidentOptionalCover().trim().equals("No"))
        {
            setPremiumPersonalAccident("0");
        }
        else
        {
            findPremiumPA(sumInsured,paCover,paSi);
        }
        if(healthCoach.equals("No"))
        {
            setPremiumHealthCoach("0");
        }
        else
        {
            findHealthCoach(healthCoach);
        }
        if(discount.equals("No"))
        {
            setDiscount("0");
        }
        else
        {
            findDiscount(discount);
        }
        findTax();
    }


    private void findTax()
    {
        JSONObject priceJson = null;
        JSONObject percentageValueJson = null;
        String taxValue = null;
        if(discountDoc != null && discountDoc.length() != 0 && discountDoc.getJSONObject(0).has(PRICE)) {
            priceJson = discountDoc.getJSONObject(0).getJSONObject(PRICE);
        }
        if(priceJson!=null && priceJson.has(PERCENTAGE_VALUE))
        {
            percentageValueJson = priceJson.getJSONObject(PERCENTAGE_VALUE);
        }
        if(percentageValueJson!=null && percentageValueJson.has("Tax"))
        {
            taxValue = percentageValueJson.getString("Tax");
        }
        if(Strings.isNullOrEmpty(taxValue))
        {
            throw new RecordNotFoundException(RECORD_NOT_FOUND_EXCEPTION + FOR_PRODUCTID + productId);
        }
        setTax(taxValue);
    }


    private void findDiscount(String discount)
    {
        JSONObject priceJson = null;
        JSONObject percentageValueJson = null;
        String discountValue = null;
        if(discountDoc != null && discountDoc.length() != 0 && discountDoc.getJSONObject(0).has(PRICE)) {
            priceJson = discountDoc.getJSONObject(0).getJSONObject(PRICE);
        }
        if(priceJson!=null && priceJson.has(PERCENTAGE_VALUE))
        {
            percentageValueJson = priceJson.getJSONObject(PERCENTAGE_VALUE);
        }
        if(percentageValueJson!=null && percentageValueJson.has(discount))
        {
            discountValue = percentageValueJson.getString(discount);
        }
        if(Strings.isNullOrEmpty(discountValue))
        {
            throw new RecordNotFoundException(RECORD_NOT_FOUND_EXCEPTION + FOR_PRODUCTID + productId);
        }
        setDiscount(discountValue);
    }

    private void findAdvantageDiscount()
    {
        JSONObject priceJson = null;
        JSONObject percentageValueJson = null;
        String advantageDiscountValue = null;
        if(discountDoc != null && discountDoc.length() != 0 && discountDoc.getJSONObject(0).has(PRICE)) {
            priceJson = discountDoc.getJSONObject(0).getJSONObject(PRICE);
        }
        if(priceJson!=null && priceJson.has(PERCENTAGE_VALUE))
        {
            percentageValueJson = priceJson.getJSONObject(PERCENTAGE_VALUE);
        }
        if(percentageValueJson!=null && percentageValueJson.has("Advantage"))
        {
            advantageDiscountValue = percentageValueJson.getString("Advantage");
        }
        if(Strings.isNullOrEmpty(advantageDiscountValue))
        {
            throw new RecordNotFoundException(RECORD_NOT_FOUND_EXCEPTION + FOR_PRODUCTID + productId);
        }
        setAdvantageDiscount(advantageDiscountValue);
    }


    private void findHealthCoach(String healthCoach)
    {
        JSONObject priceJson = null;
        JSONObject healthCoachValueJson = null;
        String healthCoachPremium = null;

        if(healthCoachDoc != null && healthCoachDoc.length() != 0 && healthCoachDoc.getJSONObject(0).has(PRICE)) {
            priceJson = healthCoachDoc.getJSONObject(0).getJSONObject(PRICE);
        }
        if(priceJson!=null && priceJson.has("HealthCoachValue"))
        {
            healthCoachValueJson = priceJson.getJSONObject("HealthCoachValue");
        }
        if(healthCoachValueJson!=null && healthCoachValueJson.has(healthCoach))
        {
            healthCoachPremium = healthCoachValueJson.getString(healthCoach);
        }
        if(Strings.isNullOrEmpty(healthCoachPremium))
        {
            throw new RecordNotFoundException(RECORD_NOT_FOUND_EXCEPTION + FOR_PRODUCTID + productId);
        }
        setPremiumHealthCoach(healthCoachPremium);
    }


    private void findPremiumPA(String sumInsured, String paCover, String paSi)
    {
        JSONObject priceJson = null;
        JSONObject siJson = null;
        JSONObject paCoverJson = null;
        String paValue = null;
        if(personalAccidentDoc != null && personalAccidentDoc.length() != 0 && personalAccidentDoc.getJSONObject(0).has(PRICE)) {
            priceJson = personalAccidentDoc.getJSONObject(0).getJSONObject(PRICE);
        }
        if(priceJson!=null && priceJson.has(sumInsured))
        {
            siJson = priceJson.getJSONObject(sumInsured);
        }

        if(siJson!=null && siJson.has(paCover))
        {
            paCoverJson = siJson.getJSONObject(paCover);
        }
        if(paCoverJson!=null && paCoverJson.has(paSi))
        {
            paValue = paCoverJson.getString(paSi);
        }
        if(Strings.isNullOrEmpty(paValue))
        {
            throw new RecordNotFoundException(RECORD_NOT_FOUND_EXCEPTION + FOR_PRODUCTID + productId);
        }
        setPremiumPersonalAccident(paValue);
    }

    private void findDeductible(String sumInsured, String deductible)
    {
        JSONObject priceJson = null;
        JSONObject deductibleJson = null;
        String deductibleMultiplicativeFactorValue = null;
        if(deductibleDoc != null && deductibleDoc.length() != 0 && deductibleDoc.getJSONObject(0).has(PRICE))
        {
            priceJson = deductibleDoc.getJSONObject(0).getJSONObject(PRICE);
        }
        if(priceJson!=null && priceJson.has(deductible))
        {
            deductibleJson = priceJson.getJSONObject(deductible);
        }
        if(deductibleJson!=null && deductibleJson.has(sumInsured))
        {
            deductibleMultiplicativeFactorValue = deductibleJson.getString(sumInsured);
        }
        if(Strings.isNullOrEmpty(deductibleMultiplicativeFactorValue))
        {
            throw new RecordNotFoundException(RECORD_NOT_FOUND_EXCEPTION + FOR_PRODUCTID + productId);
        }
        setDeductibleMultiplicativeFactor(deductibleMultiplicativeFactorValue);
    }

    private void findPremiumfOldestMember(String ageOfEldestMember, String sumInsured)
    {
        JSONObject eldestAgeDoc = null;
        JSONObject priceJson = null;
        String basePremium = null;
        if(basePremiumDoc != null && basePremiumDoc.length() != 0 && basePremiumDoc.getJSONObject(0).has(PRICE))
        {
            priceJson = basePremiumDoc.getJSONObject(0).getJSONObject(PRICE);
        }
        if(priceJson!=null && priceJson.has(ageOfEldestMember))
        {
            eldestAgeDoc = priceJson.getJSONObject(ageOfEldestMember);
        }
        if(eldestAgeDoc!=null && eldestAgeDoc.has(sumInsured))
        {
            basePremium = eldestAgeDoc.getString(sumInsured);
        }
        if (Strings.isNullOrEmpty(basePremium)) {
            throw new RecordNotFoundException(RECORD_NOT_FOUND_EXCEPTION + FOR_PRODUCTID + productId);
        }
        setBasePremiumEldestMember(basePremium);
    }

    private void fetchBasePremiumDocument(GoActiveModel goActiveModel) {
        String familyCombination = goActiveModel.getFamilyCombination().trim();
        String variant = goActiveModel.getVariant();
        String zone = goActiveModel.getZone();
        JSONArray basePremiumDocument;
        Query basePremiun = new Query();
        basePremiun.fields().exclude("_id");
        basePremiun.addCriteria(
                new Criteria().andOperator(
                        Criteria.where(PRODUCT_ID).is(productId),
                        Criteria.where("FamilyCombination").is(familyCombination),
                        Criteria.where("Variant").is(variant),
                        Criteria.where("Zone").is(zone)
                )
        );
        basePremiumDocument = new JSONArray(mongoTemplate.find(basePremiun, Object.class, MAXBUPA_PRODUCTS));
        if (basePremiumDocument.length() == 0) {
            throw new RecordNotFoundException(RECORD_NOT_FOUND_EXCEPTION + FOR_PRODUCTID + productId);
        }
        basePremiumDoc = basePremiumDocument;
    }
}
