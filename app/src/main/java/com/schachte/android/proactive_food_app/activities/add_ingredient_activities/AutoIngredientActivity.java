package com.schachte.android.proactive_food_app.activities.add_ingredient_activities;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.schachte.android.proactive_food_app.R;
import com.schachte.android.proactive_food_app.database.DataAccessLayer;
import com.schachte.android.proactive_food_app.models.Ingredient;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AutoIngredientActivity extends AppCompatActivity {

    private final String BEGIN_URL = "https://pod.opendatasoft.com/api/records/1.0/search/?dataset=pod_gtin&q=";

    private EditText nameOfIngredient;
    private ImageView imageView;

    private String ingredientName;
    private String ingredientURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_ingredient);

        final String gtin_id = getIntent().getStringExtra("JSON_ID");

        imageView = (ImageView)findViewById(R.id.autoImageView);
        nameOfIngredient = (EditText)findViewById(R.id.autoTextView);

        new DownloadJSONTask().execute(BEGIN_URL + gtin_id);

        // TODO: make the view automatically load this, and have button add to DB and close view
        Button autoAddButton = (Button)findViewById(R.id.autoIngredientButton);
        autoAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIngredient();
            }
        });

    }

    private void addIngredient() {
        EditText tv = this.nameOfIngredient;
        Log.i(tv.getText().toString(), tv.getText().toString());
        if (tv.getText().length() == 0) {
            Toast.makeText(this, "You must put some name of an ingredient!", Toast.LENGTH_LONG).show();
        } else {
            String nameOfIngredient = tv.getText().toString();
            Ingredient toAdd = new Ingredient();
            toAdd.setIngredientName(nameOfIngredient);

            // I think this is okay for now...
            toAdd.setIngredientGeneralName(nameOfIngredient);

            toAdd.setIngredientId(0);

            BitmapDrawable drawable = (BitmapDrawable)imageView.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();
            String base64 = null;
            try {
                base64 = new String(bytes, "UTF-8");
                toAdd.setIngredientImageBytes(base64);

                toAdd.setIngredientImageURL(ingredientURL);

                DataAccessLayer dal = new DataAccessLayer(this);
                dal.storeIngredient(toAdd);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            finish();
        }
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
