package com.maxbupa.apiservices.services.product;

import com.maxbupa.apiservices.utilities.ExcelToJsonExporterMdUtility;
import com.maxbupa.apiservices.model.product.request.BasePremium;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    private MongoTemplate mongoTemplate;

    @Autowired
    public ProductServiceImpl(MongoTemplate mongoTemplate)
    {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public void insertProductJsonMdWithoutDeductible(String filePath) {
        List<String> product = ExcelToJsonExporterMdUtility.readWorkbookConvertJsonWithOutDeductible(filePath);
        mongoTemplate.insert(product , "maxbupaProducts");
    }

    @Override
    public void insertProductJsonWithDeductible(String filePath) {
        List<String> product = ExcelToJsonExporterMdUtility.readWorkbookConvertJsonWithDeductible(filePath);
        mongoTemplate.insert(product , "maxbupaProducts");
    }

    @Override
    public List<Object> getProductById(String productID) {
        Query q = new Query();
        new Criteria();
        q.addCriteria(Criteria.where("ProductId").is(productID));
        q.fields().exclude("_id");
        return mongoTemplate.find(q, Object.class, "products");
    }

    @Override
    public ArrayList<String> sumInsuredRange(String productID) {
        Query q = new Query();
        new Criteria();
        q.addCriteria(Criteria.where("ProductId").is(productID));
        q.fields().exclude("_id");
        JSONArray productjson = new JSONArray(mongoTemplate.find(q, Object.class, "products"));
        Map<String, Object> sumInsured = productjson.getJSONObject(0).getJSONObject("price").toMap();
        String sumInsuredRange = sumInsured.entrySet().stream().findFirst().get().getValue().toString();
        int siLength = sumInsuredRange.toCharArray().length;
        String sumInsuredWithKeyValue = sumInsuredRange.substring(1,siLength-1);
        String[] si = sumInsuredWithKeyValue.split(",");
        ArrayList<String> sumInsuredRanges = new ArrayList<>();
        for(int i=0; i<=si.length-1; i++)
        {
            int endCharPosition = si[i].indexOf("=");
            sumInsuredRanges.add(si[i].substring(0,endCharPosition));
        }
        return sumInsuredRanges;
    }

    @Override
    public String getBasePremium(BasePremium basePremium) {
        Query q = new Query();
        q.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("ProductId").is(basePremium.getProductId()),
                        Criteria.where("Variant").is(basePremium.getVariant()),
                        Criteria.where("FamilyCombination").is(basePremium.getFamilyCombination()),
                        Criteria.where("Zone").is(basePremium.getZone())
                )
        );
        q.fields().exclude("_id");
        JSONArray productjson = new JSONArray(mongoTemplate.find(q, Object.class, "products"));
        return productjson
                .getJSONObject(0)
                .getJSONObject("price")
                .getJSONObject(basePremium.getAge())
                .get(basePremium.getSumInsured())
                .toString();
    }
}
