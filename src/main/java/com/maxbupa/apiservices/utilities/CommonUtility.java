package com.maxbupa.apiservices.utilities;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CommonUtility {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtility.class);

    private CommonUtility(){

    }
    //get the current date and time
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ", locale = "en_IN",timezone = "Asia/Kolkata")
    public static Date getCurrentDateTime(){
        return new Date();
    }

    //generate UUID
    public static String getUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static List<String> phoneNumberList(List<String> to)
    {
        List<String> phoneNumberString = new ArrayList<>();
        int size =100;
        for(int start =0; start < to.size(); start += size)
        {
            int end = Math.min(start + size, to.size());
            List<String> sublist = to.subList(start, end);
            phoneNumberString.add(convertListToString(sublist));
        }
        return phoneNumberString;

    }

    private static String convertListToString(List<String> sublist)
    {
        return sublist.stream().map(number -> String.valueOf(number)).collect(Collectors.joining(","));
    }

    public static String getHttp(String url)
    {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        StringBuilder stringBuilder = new StringBuilder();
        try {

            HttpResponse response = httpClient.execute(request);
            response.getStatusLine().getStatusCode();
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
           LOGGER.error("IOException in CommonUtility.getHttp() {}",e);
        }
        return stringBuilder.toString();
    }


    public static String postHttp(String url)
    {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        StringBuilder stringBuilder = new StringBuilder();
        try {

            HttpResponse response = httpClient.execute(post);
            response.getStatusLine().getStatusCode();
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            LOGGER.error("IOException in CommonUtility.postHttp() {}",e);
        }
        return stringBuilder.toString();
    }
}