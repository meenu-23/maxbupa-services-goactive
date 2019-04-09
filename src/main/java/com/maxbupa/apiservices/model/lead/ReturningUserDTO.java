package com.maxbupa.apiservices.model.lead;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class ReturningUserDTO {

    private String mcid;
    private String customerId;
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

    @JsonFormat( locale = "en_IN",timezone = "Asia/Kolkata")
    private Date dateTime;

    private List<ProductsViewedDTO> productsViewed;


    public String getMcid() {
        return mcid;
    }

    public void setMcid(String mcid) {
        this.mcid = mcid;
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

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public List<ProductsViewedDTO> getProductsViewed() {
        return productsViewed;
    }

    public void setProductsViewed(List<ProductsViewedDTO> productsViewed) {
        this.productsViewed = productsViewed;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
