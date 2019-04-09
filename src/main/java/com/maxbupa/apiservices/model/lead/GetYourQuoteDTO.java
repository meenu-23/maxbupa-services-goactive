package com.maxbupa.apiservices.model.lead;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GetYourQuoteDTO {

    private String mcid;
    private String customerId;
    private GetYourQuoteFormDetailsDTO forms;
    private String consistencyScore;
    private String dispositionScore;
    private String lastVisitedSite;
    private String sourceReferrer;
    private String source;
    private String subSource;
    private String lifeTimeSource;
    private String[] subsequentSources;
    private String lastPageVisited;
    private String location;
    private String browser;
    private String operatingSystem;
    private List<ProductsViewedDTO> productsViewed;


    public String getMcid() {
        return mcid;
    }

    public void setMcid(String mcid) {
        this.mcid = mcid;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @JsonProperty("forms")
    public GetYourQuoteFormDetailsDTO getGetYourQuoteFormDetails() {
        return forms;
    }


    @JsonProperty("forms")
    public void setGetYourQuoteFormDetails(GetYourQuoteFormDetailsDTO getYourQuoteFormDetails) {
        this.forms = getYourQuoteFormDetails;
    }

    public GetYourQuoteFormDetailsDTO getForms() {
        return forms;
    }

    public void setForms(GetYourQuoteFormDetailsDTO forms) {
        this.forms = forms;
    }

    public String getConsistencyScore() {
        return consistencyScore;
    }

    public void setConsistencyScore(String consistencyScore) {
        this.consistencyScore = consistencyScore;
    }

    public String getDispositionScore() {
        return dispositionScore;
    }

    public void setDispositionScore(String dispositionScore) {
        this.dispositionScore = dispositionScore;
    }

    public String getLastVisitedSite() {
        return lastVisitedSite;
    }

    public void setLastVisitedSite(String lastVisitedSite) {
        this.lastVisitedSite = lastVisitedSite;
    }

    public String getSourceReferrer() {
        return sourceReferrer;
    }

    public void setSourceReferrer(String sourceReferrer) {
        this.sourceReferrer = sourceReferrer;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSubSource() {
        return subSource;
    }

    public void setSubSource(String subSource) {
        this.subSource = subSource;
    }

    public String getLifeTimeSource() {
        return lifeTimeSource;
    }

    public void setLifeTimeSource(String lifeTimeSource) {
        this.lifeTimeSource = lifeTimeSource;
    }

    public String[] getSubsequentSources() {
        return subsequentSources;
    }

    public void setSubsequentSources(String[] subsequentSources) {
        this.subsequentSources = subsequentSources;
    }

    public String getLastPageVisited() {
        return lastPageVisited;
    }

    public void setLastPageVisited(String lastPageVisited) {
        this.lastPageVisited = lastPageVisited;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public List<ProductsViewedDTO> getProductsViewed() {
        return productsViewed;
    }

    public void setProductsViewed(List<ProductsViewedDTO> productsViewed) {
        this.productsViewed = productsViewed;
    }
}
