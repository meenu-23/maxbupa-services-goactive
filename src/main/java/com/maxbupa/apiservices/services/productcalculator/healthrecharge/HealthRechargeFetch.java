package com.maxbupa.apiservices.services.productcalculator.healthrecharge;

import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.maxbupa.apiservices.exception.RecordNotFoundException;
import com.maxbupa.apiservices.model.productcalculator.healthrecharge.HealthRechargeModel;
import com.maxbupa.apiservices.model.productcalculator.healthrecharge.HealthRechargeResponseThreeYears;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HealthRechargeFetch extends HealthRechargeServiceImpl {

    private static final int MAX_AGE = 65;
    private static final String TYPE_DISCOUNT = "Discount";
    private static final String TYPE_PA = "PA";
    private static final String TYPE_CI = "CI";
    private static final String TYPE_MRR = "MRR";
    private static final String TERM_DISCOUNT_2YR = "TermDiscount2yr";
    private static final String TERM_DISCOUNT_3YR = "TermDiscount3yr";
    private static final String PRICE = "price";
    private static final String PRODUCT_ID = "ProductId";
    private static final String MAXBUPA_PRODUCTS = "maxbupaProducts";
    private static final String RECORD_NOT_FOUND_EXCEPTION = "Record Not Found";
    private static final String INVALID_AGE = "Age Should Not Exceed 65";
    private static final String FOR_PRODUCTID = " For ProductId ";
    private static final String PERCENTAGE_VALUE = "PercentageValue";
    private MongoTemplate mongoTemplate;
    private String productId;
    private String twoYearDscnt;
    private String threeYearDscnt;
    private JSONArray discountDocument;
    private JSONArray personalAccidentDocument;
    private JSONArray criticalIllnessDocument;
    private JSONArray modificationRoonRentDocument;
    private JSONArray basePremiumDocument;


    @Autowired
    public HealthRechargeFetch(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;

    }

    @Override
    public HealthRechargeResponseThreeYears calculatePremium(HealthRechargeModel healthRechargeModel) {
        fetchValues(healthRechargeModel);
        return calPremium(healthRechargeModel);
    }

    private void fetchLoadingfactor() {
        Query optionalRateDoc = new Query();
        optionalRateDoc.fields().exclude("_id");
        optionalRateDoc.addCriteria(
                new Criteria().andOperator(
                        Criteria.where(PRODUCT_ID).is(productId),
                        Criteria.where("Type").exists(true)
                )
        );
        JSONArray loading = new JSONArray(mongoTemplate.find(optionalRateDoc, Object.class, MAXBUPA_PRODUCTS));
        if (loading.length()!=0) {
            for (int noOfDocInDb = 0; noOfDocInDb < loading.length(); noOfDocInDb++) {

                switch (loading.getJSONObject(noOfDocInDb).get("Type").toString()) {
                    case TYPE_DISCOUNT:
                        JSONArray discountDoc = new JSONArray();
                        discountDoc.put(loading.getJSONObject(noOfDocInDb));
                        discountDocument = discountDoc;
                        break;
                    case TYPE_PA:
                        JSONArray personalAccidentDoc = new JSONArray();
                        personalAccidentDoc.put(loading.getJSONObject(noOfDocInDb));
                        personalAccidentDocument = personalAccidentDoc;
                        break;
                    case TYPE_CI:
                        JSONArray criticalIllnessDoc = new JSONArray();
                        criticalIllnessDoc.put(loading.getJSONObject(noOfDocInDb));
                        criticalIllnessDocument = criticalIllnessDoc;
                        break;
                    case TYPE_MRR:
                        JSONArray modificationRoonRentDoc = new JSONArray();
                        modificationRoonRentDoc.put(loading.getJSONObject(noOfDocInDb));
                        modificationRoonRentDocument = modificationRoonRentDoc;
                        break;
                    default:
                       break;
                }
            }
        }
    }


    private void fetchValues(HealthRechargeModel healthRechargeModel) {
        productId = healthRechargeModel.getProductId().trim();
        setProductId(productId);
        setComponentId(healthRechargeModel.getComponentId().trim());
        String si = healthRechargeModel.getSi().trim();
        String personalAccident = healthRechargeModel.getPersonalAccident().trim();
        String paSi = healthRechargeModel.getPaSi().trim();
        String ci = healthRechargeModel.getCi().trim();
        String ciSi = healthRechargeModel.getCiSi().trim();
        String ageOfEldestMember = healthRechargeModel.getAgeOfEldestMember().trim();
        String ageOfSpouse = healthRechargeModel.getAgeOfSpouse().trim();
        String ageOfPrimaryInsured = healthRechargeModel.getAgeOfPrimaryInsured().trim();
        String modificationRoomRentFlag = healthRechargeModel.getModificationRoomRent().trim();
        String discount = healthRechargeModel.getDiscount().trim();
        setDiscountType(discount);
        if (Integer.parseInt(ageOfEldestMember) > MAX_AGE || Integer.parseInt(ageOfSpouse) > MAX_AGE || Integer.parseInt(ageOfPrimaryInsured) > MAX_AGE) {
            throw new RecordNotFoundException(INVALID_AGE + FOR_PRODUCTID + productId);
        } else {
            fetchLoadingfactor();
            findTax();
            if (discount.equals("No")) {
                setDiscount("0");
            } else {
                setDiscount(findDiscount(discount));
            }
            twoYearDscnt = calTwoOrThreeYear(TERM_DISCOUNT_2YR);
            threeYearDscnt = calTwoOrThreeYear(TERM_DISCOUNT_3YR);
            setSecondYearDsct(twoYearDscnt);
            setThirdYearDsct(threeYearDscnt);
            List<String> basePremiumForThreeYears = getBasePremiumForThreeYears(healthRechargeModel);
            setFirstYearPrm(basePremiumForThreeYears.get(0));
            setSecondYearPrm(basePremiumForThreeYears.get(1));
            setThirdYearPrm(basePremiumForThreeYears.get(2));
            setPafirstYearPrm(getPaFirstYear(si, personalAccident, paSi));
            List<String> ciForThreeYears = getCiFirstYear(si, ci, ciSi, ageOfEldestMember, ageOfSpouse, ageOfPrimaryInsured);
            setCiFirstYearPrm(ciForThreeYears.get(0));
            setCiSecondYearPrm(ciForThreeYears.get(1));
            setCiThirdYearPrm(ciForThreeYears.get(2));
            setModRoomRentPremium(modificationRoomRent(modificationRoomRentFlag, si));
            clearLoadingFactorJson();
        }
    }

    private void clearLoadingFactorJson()
    {
        clearJsonArray(discountDocument);
        clearJsonArray(personalAccidentDocument);
        clearJsonArray(criticalIllnessDocument);
        clearJsonArray(modificationRoonRentDocument);
        clearJsonArray(basePremiumDocument);
    }

    private void clearJsonArray(JSONArray jsonArray)
    {
        if(jsonArray!=null && jsonArray.length() > 0)
        {
            for(int i=0; i<=jsonArray.length()-1; i++)
            {
                jsonArray.remove(i);
            }
        }
    }

    private String modificationRoomRent(String modificationRoomRentFlag, String si) {
        String modificationRoomRentValue = null;
        if (modificationRoomRentFlag.equals("Yes")) {
            modificationRoomRentValue = validateModificationRoomRent(si);
            return modificationRoomRentValue;
        } else {
            modificationRoomRentValue = "0";
            return modificationRoomRentValue;
        }
    }

    private String validateModificationRoomRent(String si)
    {
        JSONObject modificationRoonRentJson = null;
        JSONObject priceJson = null;
        JSONObject percentageJson = null;
        String modificationRoomRentValue = null;
        if (modificationRoonRentDocument!=null && modificationRoonRentDocument.length() != 0) {
            modificationRoonRentJson = modificationRoonRentDocument.getJSONObject(0);
        }
        if (modificationRoonRentJson != null && modificationRoonRentJson.has(PRICE)) {
            priceJson = modificationRoonRentJson.getJSONObject(PRICE);
        }
        if (priceJson != null && priceJson.has("Percentage")) {
            percentageJson = priceJson.getJSONObject("Percentage");
        }
        if (percentageJson != null && percentageJson.has(si)) {
            modificationRoomRentValue = percentageJson.getString(si);
        }
        if (Strings.isNullOrEmpty(modificationRoomRentValue)) {
            throw new RecordNotFoundException(RECORD_NOT_FOUND_EXCEPTION + FOR_PRODUCTID + productId);
        }
        return modificationRoomRentValue;
    }

    private List<String> getCiFirstYear(String si, String ci, String ciSi, String ageOfEldestMember, String ageOfSpouse, String ageOfPrimaryInsured) {
        int ageOfPrimaryInsuredFound = Integer.parseInt(ageOfPrimaryInsured);
        int ageOfEldestMemberFound = Integer.parseInt(ageOfEldestMember);
        int ageOfSpouseFound = Integer.parseInt(ageOfSpouse);
        List<String> ciThreeYears = new ArrayList<>();
        if (ci.equals("No")) {
            addZeroStringToList(ciThreeYears);
            return ciThreeYears;
        }
        if (ci.equals("1A") || ci.equals("2A")) {
            if (ci.equals("1A")) {
               return calculateCi1A( ageOfPrimaryInsuredFound,  si,  ciSi );
            } else {
                return calculateCi2A(ageOfEldestMemberFound, ageOfSpouseFound, si, ciSi );
            }
        } else {
            throw new RecordNotFoundException("Incorrect CI");
        }
    }

    private List<String> calculateCi1A(int ageOfPrimaryInsuredFound, String si, String ciSi )
    {
        List<String> ciThreeYears = new ArrayList<>();
        for (int year = 0; year < 3; year++) {
            String agePrimaryInsured = Integer.toString(ageOfPrimaryInsuredFound + year);
            String ciValue = getCiValue(si, agePrimaryInsured, ciSi);
            int intFirstAdultCi = Integer.parseInt(ciValue);
            if (year == 1 && twoYearDscnt.equals("N/A") || year == 2 && threeYearDscnt.equals("N/A")) {
                ciThreeYears.add("N/A");
            } else {
                int criticalIllnessAdultOne = addDiscount1A(year, intFirstAdultCi);
                ciThreeYears.add(Integer.toString(criticalIllnessAdultOne));
            }
        }
        return ciThreeYears;
    }

    private List<String> calculateCi2A(int ageOfEldestMemberFound, int ageOfSpouseFound, String si, String ciSi )
    {
        List<String> ciThreeYears = new ArrayList<>();
        for (int year = 0; year < 3; year++) {
            String eldestMember = Integer.toString(ageOfEldestMemberFound + year);
            String spouseAge = Integer.toString(ageOfSpouseFound + year);
            String firstAdultCi = getCiValue(si, eldestMember, ciSi);
            String secondAdultCi = getCiValue(si, spouseAge, ciSi);
            int intFirstAdultCi = Integer.parseInt(firstAdultCi);
            int intSecondAdultCi = Integer.parseInt(secondAdultCi);
            if (year == 1 && twoYearDscnt.equals("N/A") || year == 2 && threeYearDscnt.equals("N/A")) {
                ciThreeYears.add("N/A");
            } else {
                int criticalIllness = addDiscount2A(year, intFirstAdultCi, intSecondAdultCi);
                ciThreeYears.add(Integer.toString(criticalIllness));
            }
        }
        return ciThreeYears;
    }

    private String getCiValue(String si, String age, String ciSi) {
        JSONObject ciValueJson = null;
        JSONObject priceJson = null;
        JSONObject siJson = null;
        JSONObject ageJson = null;
        String ciValue = null;
        if (criticalIllnessDocument!=null && criticalIllnessDocument.length() != 0) {
            ciValueJson = criticalIllnessDocument.getJSONObject(0);
        }
        if (ciValueJson != null && ciValueJson.has(PRICE)) {
            priceJson = ciValueJson.getJSONObject(PRICE);
        }
        if (priceJson != null && priceJson.has(si)) {
            siJson = priceJson.getJSONObject(si);
        }
        if (siJson != null && siJson.has(age)) {
            ageJson = siJson.getJSONObject(age);
        }
        if (ageJson != null && ageJson.has(ciSi)) {
            ciValue = ageJson.getString(ciSi);
        }
        if (Strings.isNullOrEmpty(ciValue)) {
            throw new RecordNotFoundException(RECORD_NOT_FOUND_EXCEPTION + FOR_PRODUCTID + productId);
        }
        return ciValue;
    }

    private int addDiscount2A(int year, int firstAdultCi, int secondAdultCi) {
        switch (year) {
            case 0:
                return firstAdultCi + secondAdultCi;
            case 1:
                double secondYearDiscount = Double.parseDouble(twoYearDscnt);
                double secondYearDt = secondYearDiscount / 100;
                int firstAdultSecondYr = (int) Math.round(firstAdultCi * (1 - secondYearDt));
                int secondAdultSecondYr = (int) Math.round(secondAdultCi * (1 - secondYearDt));
                return firstAdultSecondYr + secondAdultSecondYr;
            case 2:
                double thirdYearDiscount = Double.parseDouble(threeYearDscnt);
                double thirdYearDt = thirdYearDiscount / 100;
                int firstAdultThirdYr = (int) Math.round(firstAdultCi * (1 - thirdYearDt));
                int secondAdultThirdYr = (int) Math.round(secondAdultCi * (1 - thirdYearDt));
                return firstAdultThirdYr + secondAdultThirdYr;
            default:
                throw new RecordNotFoundException(RECORD_NOT_FOUND_EXCEPTION + FOR_PRODUCTID + productId);
        }
    }

    private int addDiscount1A(int year, int firstAdultCi) {
        switch (year) {
            case 0:
                return firstAdultCi;
            case 1:
                double secondYearDiscount = Double.parseDouble(twoYearDscnt);
                double secondYearDt = secondYearDiscount / 100;
                return (int) Math.round(firstAdultCi * (1 - secondYearDt));

            case 2:
                double thirdYearDiscount = Double.parseDouble(threeYearDscnt);
                double thirdYearDt = thirdYearDiscount / 100;
                return (int) Math.round(firstAdultCi * (1 - thirdYearDt));
            default:
                throw new RecordNotFoundException(RECORD_NOT_FOUND_EXCEPTION + FOR_PRODUCTID + productId);
        }
    }


    private List<String> addZeroStringToList(List<String> premiumPrice) {
        for (int i = 0; i <= 2; i++) {
            premiumPrice.add("0");
        }
        return premiumPrice;
    }

    private String getPaFirstYear(String si, String personalAccident, String paSi) {

        if (personalAccident.equals("No")) {
            return "0";
        } else {
            return validatePaFirstYear(si, personalAccident, paSi);
        }
    }

    private String validatePaFirstYear(String si, String personalAccident, String paSi) {
        JSONObject priceJson = null;
        JSONObject siJson = null;
        JSONObject personalAccidentDocJson = null;
        String paFirstYear = null;
        JSONObject personalAccidentJson = null;
        if (personalAccidentDocument!=null && personalAccidentDocument.length() != 0) {
            personalAccidentJson = personalAccidentDocument.getJSONObject(0);
        }
        if (personalAccidentJson != null && personalAccidentJson.has(PRICE)) {
            priceJson = personalAccidentJson.getJSONObject(PRICE);
        }
        if (priceJson != null && priceJson.has(si)) {
            siJson = priceJson.getJSONObject(si);
        }
        if (siJson != null && siJson.has(personalAccident)) {
            personalAccidentDocJson = siJson.getJSONObject(personalAccident);
        }
        if (personalAccidentDocJson != null && personalAccidentDocJson.has(paSi)) {
            paFirstYear = personalAccidentDocJson.getString(paSi);
        }
        if (Strings.isNullOrEmpty(paFirstYear)) {
            throw new RecordNotFoundException("Incorrect PersonalAccident" + FOR_PRODUCTID + productId);
        }
        return paFirstYear;
    }

    private List<String> getBasePremiumForThreeYears(HealthRechargeModel healthRechargeModel) {
        String deductible = healthRechargeModel.getDeductible().trim();
        String ageOfEldestMember = healthRechargeModel.getAgeOfEldestMember().trim();
        String si = healthRechargeModel.getSi();
        List<String> threeYearsBasePremium = new ArrayList<>();
        fetchBasePremiumDocument(healthRechargeModel);
        String firstYearBasePremium = getBasePremiumForFirstYear(deductible, ageOfEldestMember, si);
        threeYearsBasePremium.add(firstYearBasePremium);
        int secondYearAge = Integer.parseInt(ageOfEldestMember) + 1;
        String strSecondYearAge = Integer.toString(secondYearAge);
        int thirdYearAge = Integer.parseInt(ageOfEldestMember) + 2;
        String strThirdYearAge = Integer.toString(thirdYearAge);
        String secondYearBasePremium = getBasePremiumForFirstYear(deductible, strSecondYearAge, si);
        threeYearsBasePremium.add(secondYearBasePremium);
        String thirdYearBasePremium = getBasePremiumForFirstYear(deductible, strThirdYearAge, si);
        threeYearsBasePremium.add(thirdYearBasePremium);
        return threeYearsBasePremium;
    }

    private String getBasePremiumForFirstYear(String deductible, String ageOfEldestMember, String si) {
        JSONObject priceJson = null;
        JSONObject deductibleJson = null;
        JSONObject ageOfEldestMemberJson = null;
        String basePremium = null;
        JSONObject basePremiumJson = null;
        if (basePremiumDocument!=null && basePremiumDocument.length() != 0) {
            basePremiumJson = basePremiumDocument.getJSONObject(0);
        }
        if (basePremiumJson != null && basePremiumJson.has(PRICE)) {
            priceJson = basePremiumJson.getJSONObject(PRICE);
        }
        if (priceJson != null && priceJson.has(deductible)) {
            deductibleJson = priceJson.getJSONObject(deductible);
        }
        if (deductibleJson != null && deductibleJson.has(ageOfEldestMember)) {
            ageOfEldestMemberJson = deductibleJson.getJSONObject(ageOfEldestMember);
        }
        if (ageOfEldestMemberJson != null && ageOfEldestMemberJson.has(si)) {
            basePremium = ageOfEldestMemberJson.getString(si);
        }
        if (Strings.isNullOrEmpty(basePremium)) {
            throw new RecordNotFoundException(RECORD_NOT_FOUND_EXCEPTION + FOR_PRODUCTID + productId);
        }
        return basePremium;
    }

    private void fetchBasePremiumDocument(HealthRechargeModel healthRechargeModel) {
        String familyCombination = healthRechargeModel.getFamilyCombination().trim();
        String variant = healthRechargeModel.getVariant().trim();
        String zone = healthRechargeModel.getZone().trim();
        JSONArray basePremiumDoc;
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
        basePremiumDoc = new JSONArray(mongoTemplate.find(basePremiun, Object.class, MAXBUPA_PRODUCTS));
        if (basePremiumDoc.length() == 0) {
            throw new RecordNotFoundException(RECORD_NOT_FOUND_EXCEPTION + FOR_PRODUCTID + productId);
        }
        basePremiumDocument = basePremiumDoc;
    }


    private void findTax() {
        JSONObject priceJson = null;
        JSONObject percentageValueJson = null;
        String taxValue = null;
        if (discountDocument!=null && discountDocument.length() != 0 && discountDocument.getJSONObject(0).has(PRICE)) {
            priceJson = discountDocument.getJSONObject(0).getJSONObject(PRICE);
        }
        if (priceJson != null && priceJson.has(PERCENTAGE_VALUE)) {
            percentageValueJson = priceJson.getJSONObject(PERCENTAGE_VALUE);
        }
        if (percentageValueJson != null && percentageValueJson.has("Tax")) {
            taxValue = percentageValueJson.getString("Tax");
        }
        if (Strings.isNullOrEmpty(taxValue)) {
            throw new RecordNotFoundException(RECORD_NOT_FOUND_EXCEPTION + FOR_PRODUCTID + productId);
        }
        setTax(taxValue);
    }

    private String findDiscount(String discount) {
        JSONObject priceJson = null;
        JSONObject percentageValueJson = null;
        String discountValue = null;
        if (discountDocument!=null && discountDocument.length() != 0 && discountDocument.getJSONObject(0).has(PRICE)) {
            priceJson = discountDocument.getJSONObject(0).getJSONObject(PRICE);
        }
        if (priceJson != null && priceJson.has(PERCENTAGE_VALUE)) {
            percentageValueJson = priceJson.getJSONObject(PERCENTAGE_VALUE);
        }
        if (percentageValueJson != null && percentageValueJson.has(discount)) {
            discountValue = percentageValueJson.getString(discount);
        }
        if (Strings.isNullOrEmpty(discountValue)) {
            throw new RecordNotFoundException(RECORD_NOT_FOUND_EXCEPTION + FOR_PRODUCTID + productId);
        }
        return discountValue;
    }

    private String calTwoOrThreeYear(String termYearDiscount) {
        JSONObject priceJson = null;
        JSONObject percentageValueJson = null;
        String discountValue = null;
        if (discountDocument!=null && discountDocument.length() != 0 && discountDocument.getJSONObject(0).has(PRICE)) {
            priceJson = discountDocument.getJSONObject(0).getJSONObject(PRICE);
        }
        if (priceJson != null && priceJson.has(PERCENTAGE_VALUE)) {
            percentageValueJson = priceJson.getJSONObject(PERCENTAGE_VALUE);
        }
        if (percentageValueJson != null && percentageValueJson.has(termYearDiscount)) {
            discountValue = percentageValueJson.getString(termYearDiscount);
        }
        if (Strings.isNullOrEmpty(discountValue)) {
            discountValue = "N/A";
        }
        return discountValue;
    }
}
