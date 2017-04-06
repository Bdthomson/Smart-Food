package com.schachte.android.proactive_food_app.util;

import org.json.JSONObject;

/**
 * Created by Spencer Smith on 4/5/2017.
 */

public class PostRequestParams<T> {

    private String url;
    private Object jsonRequest;

    public PostRequestParams(String url, Object jsonRequest) {
        this.url = url;
        this.jsonRequest = jsonRequest;

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getJsonRequest() {
        return jsonRequest;
    }

    public void setJsonRequest(Object jsonRequest) {
        this.jsonRequest = jsonRequest;
    }
}
