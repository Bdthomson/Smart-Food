package com.schachte.android.proactive_food_app.database;

import android.app.Activity;
import android.content.Context;
import android.support.v7.view.menu.ActionMenuItem;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Make JSON API Requests to Food API
 */


public class ClientRequests {

    private final String TAG = this.getClass().getSimpleName();
    public RequestQueue mRequestQueue;
    public StringRequest stringRequest;
    public String url = "https://world.openfoodfacts.org/api/v0/product/039400018803.json";
    public Context cntxt;

    public ClientRequests(Context cntxt) {
        Log.d(TAG, "WORKING");
        this.cntxt = cntxt;
    }

    public void makeReq() {

        mRequestQueue = Volley.newRequestQueue(this.cntxt);

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainResponse = new JSONObject(response.toString());
                    JSONObject productData = mainResponse.getJSONObject("product");
                    Object productName = productData.get("product_name");

                    Log.d(TAG, "b4");
                    Log.d(TAG, productName.toString());
                    // Toast.makeText(cntxt, productName.toString(),)
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "ERR " + error.toString());
            }
        });

        mRequestQueue.add(stringRequest);
    }


}
