package com.schachte.android.proactive_food_app.database;

import android.app.Activity;
import android.content.Context;
import android.support.v7.view.menu.ActionMenuItem;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Make JSON API Requests to Food API
 */


public class ClientRequests {

    private final String TAG = this.getClass().getSimpleName();
    public RequestQueue mRequestQueue;
    public StringRequest stringRequest;
    public String url = "https://world.openfoodfacts.org/api/v0/product/039400018803.json";
    public Context cntxt;
    public String productNameString;

    public ClientRequests(Context cntxt) {
        Log.d(TAG, "WORKING");
        this.cntxt = cntxt;
    }

    public String makeReq() throws ExecutionException, InterruptedException {
        String results = "";
        Log.d(TAG, "CALLED1");
        try {
        RequestFuture<String> future = RequestFuture.newFuture();
        mRequestQueue = Volley.newRequestQueue(this.cntxt);

        // JSONObject mainResponse = new JSONObject(response.toString());
        // JSONObject productData = mainResponse.getJSONObject("product");
        // Object productName = productData.get("product_name");
        //
        // Log.d(TAG, "b4");
        // Log.d(TAG, productName.toString());
        // productNameString = productName.toString();



        stringRequest = new StringRequest(Request.Method.GET, url, future, future);

        mRequestQueue.add(stringRequest);


            results = future.get();
        }
        catch (Exception e) {

        }
        Log.d(TAG, results + " is the result!");

        return productNameString;
    }


}
