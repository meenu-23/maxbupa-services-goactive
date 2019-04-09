package com.maxbupa.apiservices.controllers.v1.product;

import com.maxbupa.apiservices.model.product.request.BasePremium;
import com.maxbupa.apiservices.model.product.request.FilePath;
import com.maxbupa.apiservices.services.product.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/maxbupa")
public class ProductServiceController {

    private final ProductService productService;

    public ProductServiceController(ProductService productService)
    {
        this.productService = productService;
    }

    @PostMapping(value="/productsWithOutDeductible")
    public void insertProductJsonWithoutDeductible(@RequestBody FilePath filePath)
    {
        productService.insertProductJsonMdWithoutDeductible(filePath.getFilePathOfProduct());
    }

    @PostMapping(value="/productsWithDeductible")
    public void insertProductJsonWithDeductible(@RequestBody FilePath filePath)
    {
        productService.insertProductJsonWithDeductible(filePath.getFilePathOfProduct());
    }

    @GetMapping(value="/product/{productID}")
    public List<Object> getProductById(@PathVariable String productID)
    {
        return productService.getProductById(productID);
    }

    @GetMapping(value="/productSumInsuredRange/{productID}")
    public ArrayList<String> sumInsuredRange(@PathVariable String productID)
    {
       return productService.sumInsuredRange(productID);
    }

    @PostMapping(value="/productBasePremium")
    public String getBasePremium(@RequestBody BasePremium basePremium)
    {
       return productService.getBasePremium(basePremium);
    }
}