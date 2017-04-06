package com.schachte.android.proactive_food_app.util;

import android.os.AsyncTask;

import com.schachte.android.proactive_food_app.models.PantryData;
import com.schachte.android.proactive_food_app.models.Recipe;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterNameDiscoverer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Can be used to store any calls to web services that you may need to make
 *
 * Created by Spencer Smith on 4/5/2017.
 */

public class WebServices {
    private final static String BASE_SERVER_URL = "http://192.168.1.5:8080";
    private final static String GET_RECIPES = "/getRecipes";

    public List<Recipe> getRecipesFromServer( final PantryData pantryData ) {

        List<Recipe> recipeList = new ArrayList<>();
        try {

            PostRequestParams requestParams = new PostRequestParams(BASE_SERVER_URL + GET_RECIPES, pantryData);
            PostRequestTask<Recipe[]> recipeRequest = new PostRequestTask<>(Recipe[].class);

            Recipe[] recipeArray = recipeRequest.execute(requestParams).get();

            recipeList = Arrays.asList(recipeArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipeList;
    }

    /*
     * This class can be used to get data from a URL using the POST method.
     *
     * First: Create an object of this type, as seen below:
     *         PostRequestTask<Recipe[]> recipeRequest = new PostRequestTask<>(Recipe[].class);
     *
     * Second, create a PostRequestParams object, put the URL and an object that will be
     *         used as the body of the request
     */
    private class PostRequestTask<T> extends AsyncTask<PostRequestParams, Void, T> {

        private final Class<T> classType;

        public PostRequestTask(Class<T> classType) {
            this.classType = classType;
        }

        @Override
        protected T doInBackground(PostRequestParams... paramArray) {

            PostRequestParams params = paramArray[0];
            if(android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();

            T responseBody = null;
            try {
                //General creation of the rest template object
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                //Set the body of the request
                HttpEntity<Object> entity = new HttpEntity<>(params.getJsonRequest());

                //Get the response from the server
                ResponseEntity<T> serverResponse = restTemplate.exchange(params.getUrl(), HttpMethod.POST, entity, classType);

                //Set the return type
                responseBody = serverResponse.getBody();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return responseBody;
        }
    }
}
