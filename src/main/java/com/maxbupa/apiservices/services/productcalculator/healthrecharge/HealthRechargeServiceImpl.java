package com.maxbupa.apiservices.services.productcalculator.healthrecharge;

import com.maxbupa.apiservices.model.productcalculator.healthrecharge.HealthRechargeModel;
import com.maxbupa.apiservices.model.productcalculator.healthrecharge.HealthRechargeResponse;
import com.maxbupa.apiservices.model.productcalculator.healthrecharge.HealthRechargeResponseThreeYears;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public abstract class HealthRechargeServiceImpl implements HealthRechargeService {

    private String componentId;
    private String productId;
    private String firstYearPrm;
    private String secondYearPrm;
    private String thirdYearPrm;
    private String pafirstYearPrm;
    private String ciFirstYearPrm;
    private String ciSecondYearPrm;
    private String ciThirdYearPrm;
    private String modRoomRentPremium;
    private String secondYearDsct;
    private String thirdYearDsct;
    private String tax;
    private static final String NOT_APPLICABLE = "N/A";
    private List<String> basePremium;
    private List<String> personalAccidentPremium;
    private List<String> criticalIllnessPremium;
    private List<String> modificationRoomRentPremium;
    private List<String> total;
    private List<String> totalPremium;
    private String discount;
    private String discountType;
    private List<String> totalPremiumAfterDiscount;
    private List<String> premiumAfterTax;

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setFirstYearPrm(String firstYearPrm) {
        this.firstYearPrm = firstYearPrm;
    }

    public void setSecondYearPrm(String secondYearPrm) {
        this.secondYearPrm = secondYearPrm;
    }

    public void setThirdYearPrm(String thirdYearPrm) {
        this.thirdYearPrm = thirdYearPrm;
    }

    public void setPafirstYearPrm(String pafirstYearPrm) {
        this.pafirstYearPrm = pafirstYearPrm;
    }

    public void setCiFirstYearPrm(String ciFirstYearPrm) {
        this.ciFirstYearPrm = ciFirstYearPrm;
    }

    public void setCiSecondYearPrm(String ciSecondYearPrm) {
        this.ciSecondYearPrm = ciSecondYearPrm;
    }

    public void setCiThirdYearPrm(String ciThirdYearPrm) {
        this.ciThirdYearPrm = ciThirdYearPrm;
    }

    public void setModRoomRentPremium(String modRoomRentPremium) {
        this.modRoomRentPremium = modRoomRentPremium;
    }

    public void setSecondYearDsct(String secondYearDsct) {
        this.secondYearDsct = secondYearDsct;
    }

    public void setThirdYearDsct(String thirdYearDsct) {
        this.thirdYearDsct = thirdYearDsct;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    private void calBasePremium() {
        List<String> basePrem = new ArrayList<>();
        int firstYearBasePrm = Integer.parseInt(firstYearPrm);
        if (!secondYearDsct.equals(NOT_APPLICABLE) && !thirdYearDsct.equals("N/A")) {
            double secondYearDiscount = Double.parseDouble(secondYearDsct);
            double secondYearDt = secondYearDiscount / 100;
            double thirdYearDiscount = Double.parseDouble(thirdYearDsct);
            double thirdYearDt = thirdYearDiscount / 100;
            int secondYearBasePrm = (int) Math.round(Integer.parseInt(secondYearPrm) * (1 - secondYearDt));
            int thirdYearBasePrm = (int) Math.round(Integer.parseInt(thirdYearPrm) * (1 - thirdYearDt));
            basePrem.add(Integer.toString(firstYearBasePrm));
            basePrem.add(Integer.toString(secondYearBasePrm));
            basePrem.add(Integer.toString(thirdYearBasePrm));
            basePremium = basePrem;
        } else if (!secondYearDsct.equals("N/A") && thirdYearDsct.equals("N/A")) {
            double secondYearDiscount = Double.parseDouble(secondYearDsct);
            double secondYearDt = secondYearDiscount / 100;
            int secondYearBasePrm = (int) Math.round(Integer.parseInt(secondYearPrm) * (1 - secondYearDt));
            basePrem.add(Integer.toString(firstYearBasePrm));
            basePrem.add(Integer.toString(secondYearBasePrm));
            basePrem.add(NOT_APPLICABLE);
            basePremium = basePrem;
        } else if (secondYearDsct.equals("N/A") && !thirdYearDsct.equals("N/A")) {
            double thirdYearDiscount = Double.parseDouble(thirdYearDsct);
            double thirdYearDt = thirdYearDiscount / 100;
            int thirdYearBasePrm = (int) Math.round(Integer.parseInt(thirdYearPrm) * (1 - thirdYearDt));
            basePrem.add(Integer.toString(firstYearBasePrm));
            basePrem.add(NOT_APPLICABLE);
            basePrem.add(Integer.toString(thirdYearBasePrm));
            basePremium = basePrem;
        } else {
            basePrem.add(Integer.toString(firstYearBasePrm));
            basePrem.add(NOT_APPLICABLE);
            basePrem.add(NOT_APPLICABLE);
            basePremium = basePrem;
        }
    }

    private void calPAPremium() {
        ArrayList<String> paPremium = new ArrayList<>();
        int paFirstYearBasePrm = Integer.parseInt(pafirstYearPrm);
        if (!secondYearDsct.equals("N/A")&& !thirdYearDsct.equals("N/A")) {
            double secondYearDiscount = Double.parseDouble(secondYearDsct);
            double secondYearDt = secondYearDiscount / 100;
            double thirdYearDiscount = Double.parseDouble(thirdYearDsct);
            double thirdYearDt = thirdYearDiscount / 100;
            int paSecondYearBasePrm = (int) Math.round(Integer.parseInt(pafirstYearPrm) * (1 - secondYearDt));
            int paThirdYearBasePrm = (int) Math.round(Integer.parseInt(pafirstYearPrm) * (1 - thirdYearDt));
            paPremium.add(Integer.toString(paFirstYearBasePrm));
            paPremium.add(Integer.toString(paSecondYearBasePrm));
            paPremium.add(Integer.toString(paThirdYearBasePrm));
            personalAccidentPremium = paPremium;
        } else if (!secondYearDsct.equals("N/A") && thirdYearDsct.equals("N/A")) {
            double secondYearDiscount = Double.parseDouble(secondYearDsct);
            double secondYearDt = secondYearDiscount / 100;
            int paSecondYearBasePrm = (int) Math.round(Integer.parseInt(pafirstYearPrm) * (1 - secondYearDt));
            paPremium.add(Integer.toString(paFirstYearBasePrm));
            paPremium.add(Integer.toString(paSecondYearBasePrm));
            paPremium.add(NOT_APPLICABLE);
            personalAccidentPremium = paPremium;
        } else if (secondYearDsct.equals("N/A") && !thirdYearDsct.equals("N/A")) {
            double thirdYearDiscount = Double.parseDouble(thirdYearDsct);
            double thirdYearDt = thirdYearDiscount / 100;
            int paThirdYearBasePrm = (int) Math.round(Integer.parseInt(pafirstYearPrm) * (1 - thirdYearDt));
            paPremium.add(Integer.toString(paFirstYearBasePrm));
            paPremium.add(NOT_APPLICABLE);
            paPremium.add(Integer.toString(paThirdYearBasePrm));
            personalAccidentPremium = paPremium;
        } else {
            paPremium.add(Integer.toString(paFirstYearBasePrm));
            paPremium.add(NOT_APPLICABLE);
            paPremium.add(NOT_APPLICABLE);
            personalAccidentPremium = paPremium;
        }
    }

    private void calCIPremium() {
        List<String> criticalIllnessPremiumThreeYears = new ArrayList<>();
        criticalIllnessPremiumThreeYears.add(ciFirstYearPrm);
        criticalIllnessPremiumThreeYears.add(ciSecondYearPrm);
        criticalIllnessPremiumThreeYears.add(ciThirdYearPrm);
        criticalIllnessPremium = criticalIllnessPremiumThreeYears;
    }

    private void calModPremium() {
        List<String> modificationRoomRentPremiumForThreeYears = new ArrayList<>();
        double modRoomRentPrm = Double.parseDouble(modRoomRentPremium);
        double modRoomRentPrmValue = modRoomRentPrm / 100;
        List<String> basePrem = basePremium;
        for (int i = 0; i <= basePrem.size() - 1; i++) {
            if (basePrem.get(i).equals(NOT_APPLICABLE)) {
                modificationRoomRentPremiumForThreeYears.add(NOT_APPLICABLE);
            } else {
                int modPrem = (int) Math.round(Integer.parseInt(basePrem.get(i)) * modRoomRentPrmValue);
                modificationRoomRentPremiumForThreeYears.add(Integer.toString(modPrem));
            }
        }
        modificationRoomRentPremium = modificationRoomRentPremiumForThreeYears;
    }


    private void calTotal(HealthRechargeModel healthRechargeModel) {
        List<String> totalForThreeYears = new ArrayList<>();
        calBasePremium();
        if (!healthRechargeModel.getPersonalAccident().equals("No")) {
            calPAPremium();
        } else {
            personalAccidentPremium = addZeroToList(personalAccidentPremium);
        }
        if (!healthRechargeModel.getCi().equals("No")) {
            calCIPremium();
        } else {
            criticalIllnessPremium = addZeroToList(criticalIllnessPremium);
        }
        if (healthRechargeModel.getModificationRoomRent().equals("Yes")) {
             calModPremium();
        } else {
            modificationRoomRentPremium = addZeroToList(modificationRoomRentPremium);
        }
        for (int year = 0; year <= basePremium.size() - 1; year++) {
            if (year == 1 && secondYearDsct.equals(NOT_APPLICABLE) || year == 2 && thirdYearDsct.equals(NOT_APPLICABLE)) {
                totalForThreeYears.add(NOT_APPLICABLE);
            } else {
                int totalValue = Integer.parseInt(basePremium.get(year)) + Integer.parseInt(personalAccidentPremium.get(year)) + Integer.parseInt(criticalIllnessPremium.get(year)) + Integer.parseInt(modificationRoomRentPremium.get(year));
                totalForThreeYears.add(Integer.toString(totalValue));
            }
        }
        total = totalForThreeYears;
    }

    private List<String> addZeroToList(List<String> premiumPrice) {
        for (int i = 0; i <= 2; i++) {
            premiumPrice.add("0");
        }
        return premiumPrice;
    }

    private void calTotalPremium(HealthRechargeModel healthRechargeModel) {
        double discountPercentage = Double.parseDouble(discount);
        double discountValue = discountPercentage / 100;
        List<String> totalPremiumFinalValue = new ArrayList<>();
        List<String> discountList = new ArrayList<>();
        calTotal(healthRechargeModel);
        totalPremiumFinalValue.add(total.get(0));
        if (secondYearDsct.equals(NOT_APPLICABLE)) {
            totalPremiumFinalValue.add(NOT_APPLICABLE);
        } else {
            int secondYearTotal = Integer.parseInt(total.get(0)) + Integer.parseInt(total.get(1));
            totalPremiumFinalValue.add(Integer.toString(secondYearTotal));
        }
        if (secondYearDsct.equals(NOT_APPLICABLE) || thirdYearDsct.equals(NOT_APPLICABLE)) {
            totalPremiumFinalValue.add(NOT_APPLICABLE);
        } else {
            int thirdYearDiscount = Integer.parseInt(total.get(0)) + Integer.parseInt(total.get(1)) + Integer.parseInt(total.get(2));
            totalPremiumFinalValue.add(Integer.toString(thirdYearDiscount));
        }
        if (!healthRechargeModel.getDiscount().equals("No")) {
            calculateTotalPremiumAfterDiscount(totalPremiumFinalValue,discountList,discountValue);
        }
        else
        {
            totalPremiumAfterDiscount = totalPremiumFinalValue;
        }
        totalPremium = totalPremiumFinalValue;
    }

    private void calculateTotalPremiumAfterDiscount(List<String> totalPremiumFinalValue, List<String> discountList, double discountValue)
    {
        for (int i = 0; i <= totalPremiumFinalValue.size() - 1; i++) {
            if (i == 1 && totalPremiumFinalValue.get(1).equals(NOT_APPLICABLE) || i == 2 && totalPremiumFinalValue.get(2).equals(NOT_APPLICABLE)) {
                discountList.add(NOT_APPLICABLE);
            } else {
                int totalPremiumValue = Integer.parseInt(totalPremiumFinalValue.get(i));
                int termYearDiscount = (int) Math.round(totalPremiumValue * (1 - discountValue));
                discountList.add(Integer.toString(termYearDiscount));
            }
        }
        totalPremiumAfterDiscount = discountList;
    }

    protected HealthRechargeResponseThreeYears calPremium(HealthRechargeModel healthRechargeModel) {
        List<String> premiumAfterTaxForThreeYears = new ArrayList<>();
        double gst = Double.parseDouble(tax);
        double gstTaxValue = gst / 100;
        HealthRechargeResponseThreeYears healthRechargeResponseThreeYears = new HealthRechargeResponseThreeYears();
        calTotalPremium(healthRechargeModel);
        for (int i = 0; i <= totalPremiumAfterDiscount.size() - 1; i++) {
            if (i == 1 && totalPremiumAfterDiscount.get(1).equals(NOT_APPLICABLE) || i == 2 && totalPremiumAfterDiscount.get(2).equals(NOT_APPLICABLE)) {
                premiumAfterTaxForThreeYears.add(NOT_APPLICABLE);
            } else {
                int finalPremiumPrice = (int) Math.round(Integer.parseInt(totalPremiumAfterDiscount.get(i)) * (1 + gstTaxValue));
                premiumAfterTaxForThreeYears.add(Integer.toString(finalPremiumPrice));
            }
        }
        premiumAfterTax = premiumAfterTaxForThreeYears;
        List<HealthRechargeResponse> threeYearsPremium = setHealthRechargeResponse();
        if(threeYearsPremium.size() == 3)
        {
            healthRechargeResponseThreeYears.setFirstYearPremium(threeYearsPremium.get(0));
            healthRechargeResponseThreeYears.setSecondYearPremium(threeYearsPremium.get(1));
            healthRechargeResponseThreeYears.setThirdYearPremium(threeYearsPremium.get(2));
        }
        return healthRechargeResponseThreeYears;
    }

    private List<HealthRechargeResponse> setHealthRechargeResponse()
    {
        List<HealthRechargeResponse> threeYearsPremium = new ArrayList<>();
        for(int year=0; year<=2; year++)
        {
            HealthRechargeResponse healthRechargeResponse = new HealthRechargeResponse();
            healthRechargeResponse.setProductId(productId);
            healthRechargeResponse.setComponentId(componentId);
            healthRechargeResponse.setBasePremium(basePremium.get(year));
            healthRechargeResponse.setPersonalAccidentPremium(personalAccidentPremium.get(year));
            healthRechargeResponse.setCriticalIllnessPremium(criticalIllnessPremium.get(year));
            healthRechargeResponse.setModificationRoomRentPremium(modificationRoomRentPremium.get(year));
            healthRechargeResponse.setTotal(total.get(year));
            healthRechargeResponse.setDiscountType(discountType);
            healthRechargeResponse.setDiscountPercentage(discount);
            healthRechargeResponse.setTotalPremium(totalPremium.get(year));
            healthRechargeResponse.setBasePremiumAfterDiscount(totalPremiumAfterDiscount.get(year));
            healthRechargeResponse.setTax(tax);
            healthRechargeResponse.setBasePremiumAfterTax(premiumAfterTax.get(year));
            threeYearsPremium.add(healthRechargeResponse);
        }
        clearList();
        return threeYearsPremium;
    }

    private void clearList()
    {
        basePremium.clear();
        personalAccidentPremium.clear();
        criticalIllnessPremium.clear();
        modificationRoomRentPremium.clear();
        total.clear();
        totalPremium.clear();
        totalPremiumAfterDiscount.clear();
        premiumAfterTax.clear();
    }
}