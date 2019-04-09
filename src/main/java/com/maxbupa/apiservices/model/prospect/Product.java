package com.maxbupa.apiservices.model.prospect;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {

    private String productName;
    private String Instances;

    public String getProductName() {
        return productName;
    }

    public String getInstances() {
        return Instances;
    }
}
