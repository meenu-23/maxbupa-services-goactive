package com.maxbupa.apiservices.model.prospect;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Prospect {

    private String mcid;
    private Date date;
    private String city;
    private Page page;
    private Product product;

    public String getMcid() {
        return mcid;
    }

    public String getDate()  {
        String dateFormatted ="";
        try{
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
            return formatter.format(date);
        }
        catch (Exception e)
        {

        }
        return dateFormatted;
    }

    public String getCity() {
        return city;
    }

    public Page getPage() {
        return page;
    }

    public Product getProduct() {
        return product;
    }
}
