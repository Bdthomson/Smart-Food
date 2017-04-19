package com.schachte.android.proactive_food_app.activities.add_ingredient_activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.schachte.android.proactive_food_app.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AutoIngredientActivity extends AppCompatActivity {

    private final String BEGIN_URL = "https://pod.opendatasoft.com/api/records/1.0/search/?dataset=pod_gtin&q=";

    private TextView nameOfIngredient;
    private ImageView imageView;

    private String ingredientName;
    private String ingredientURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_ingredient);

        // TODO: pass in JSON_ID with the GTIN Id
        //https://pod.opendatasoft.com/api/records/1.0/search/?dataset=pod_gtin&q=0742753343156
        final String gtin_id = "0742753343156"; // getIntent().getStringExtra("JSON_ID");

        imageView = (ImageView)findViewById(R.id.autoImageView);
        nameOfIngredient = (TextView)findViewById(R.id.autoTextView);


        // TODO: make the view automatically load this, and have button add to DB and close view
        Button autoAddButton = (Button)findViewById(R.id.autoIngredientButton);
        autoAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadJSONTask().execute(BEGIN_URL + gtin_id);
            }
        });

    }

    private class DownloadJSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection)url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject reader = new JSONObject(result);

                if (reader.has("records")) {
                    JSONArray records = reader.getJSONArray("records");
                    if (records.length() > 0) {
                        JSONObject firstEntry = records.getJSONObject(0);
                        if (firstEntry.has("fields")) {
                            JSONObject fields = firstEntry.getJSONObject("fields");
                            ingredientName = fields.getString("gtin_nm");
                            ingredientURL = fields.getString("gtin_img");

                            nameOfIngredient.setText(ingredientName);

                            Picasso.with(AutoIngredientActivity.this).load(ingredientURL).into(imageView);

                        }
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
