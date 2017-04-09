package com.schachte.android.proactive_food_app.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.schachte.android.proactive_food_app.activities.recipe_list_activity.RecipeListActivity;
import com.schachte.android.proactive_food_app.database.DataAccessLayer;
import com.schachte.android.proactive_food_app.database.Preferences;
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

    /*
     * Implement this in your activity so that it gets the return object
     */
    public interface WebServiceResponseListener{
        void onTaskCompleted(Object returnObject);
    }

    private final static String BASE_SERVER_URL = "http://192.168.1.5:8080";
    private final static String GET_RECIPES = "/getRecipes";

    public void getRecipesFromServer( final PantryData pantryData, final RecipeListActivity context ) {

        try {

            //Create the request parameters for the AsyncTask
            PostRequestParams requestParams = new PostRequestParams(BASE_SERVER_URL + GET_RECIPES, pantryData);

            //Get name of user
            final String nameOfUser = Preferences.getInstance().getPreferenceString("name");

            //Create the progress dialog to show while the AsyncTask is executing
            ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Please wait...");
            progressDialog.setMessage("Wait just a second " + nameOfUser + " while we create your personalized recipe list...");

            //Pass the return type, the progress dialog, and the callback listener to the async task
            PostRequestTask<Recipe[]> recipeRequest = new PostRequestTask<>(Recipe[].class, progressDialog, context);

            //Run the async task
            recipeRequest.execute(requestParams);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * This class can be used to get data from a URL using the POST method.
     *
     * First: Create an object of this type, as seen below:
     *         PostRequestTask<Recipe[]> recipeRequest = new PostRequestTask<>(Recipe[].class);
     *
     * Second: Create a progress dialog using the context of the activity if you would like
     *         to show a dialog while the task is executing
     *
     * Third: create a PostRequestParams object, put the URL and an object that will be
     *         used as the body of the request
     */
    private class PostRequestTask<T> extends AsyncTask<PostRequestParams, Void, T> {

        private final Class<T> classType;
        private ProgressDialog ringProgressDialog;
        private WebServiceResponseListener callbackListener;

        //Constructor
        public PostRequestTask(Class<T> classType, ProgressDialog dialogToShow, WebServiceResponseListener callbackListener) {
            this.classType = classType;
            ringProgressDialog = dialogToShow;
            this.callbackListener = callbackListener;
        }

        @Override
        protected void onPreExecute() {

            ringProgressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(final T returnValue) {

            //Return the object to whatever activity called this

            if( callbackListener != null )
                callbackListener.onTaskCompleted(returnValue);

            if( ringProgressDialog.isShowing() )
                ringProgressDialog.dismiss();

            super.onPostExecute(returnValue);

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
