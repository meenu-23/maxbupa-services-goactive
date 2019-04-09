package com.maxbupa.apiservices.model.lead;

public class ProductsViewedDTO {

    private String productName;
    private String productViewedDate;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductViewedDate() {
        return productViewedDate;
    }

    public void setProductViewedDate(String productViewedDate) {
        this.productViewedDate = productViewedDate;
    }
}
