package com.maxbupa.apiservices.services.product;

import com.maxbupa.apiservices.model.product.request.BasePremium;

import java.util.ArrayList;
import java.util.List;

public interface ProductService {

    void insertProductJsonMdWithoutDeductible(String filePath);
    void insertProductJsonWithDeductible(String filePath);
    List<Object> getProductById(String productID);
    ArrayList<String> sumInsuredRange(String productID);
    String getBasePremium(BasePremium basePremium);

}
