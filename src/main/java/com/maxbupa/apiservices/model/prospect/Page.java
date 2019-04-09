package com.maxbupa.apiservices.model.prospect;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Page {

    private String pageName;
    private String pageViews;

    public String getPageName() {
        return pageName;
    }

    public String getPageViews() {
        return pageViews;
    }
}
