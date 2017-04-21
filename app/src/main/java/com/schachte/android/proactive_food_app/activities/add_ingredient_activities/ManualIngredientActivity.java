package com.schachte.android.proactive_food_app.activities.add_ingredient_activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;

public class ManualIngredientActivity extends Activity {

    private EditText normalName;
    private EditText generalName;

    private ImageView chosenImageView;
    private Button loadChosenImageButton;

    private String imageURL = "none";

    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_ingredient);

        Button addIngredientButton = (Button) findViewById(R.id.addManualIngredientBtn);

        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addIngredient();
            }
        });

        normalName = (EditText)findViewById(R.id.nameOfIngredient);
        generalName = (EditText)findViewById(R.id.manualGeneralTextView);

        chosenImageView = (ImageView)findViewById(R.id.manualImageView);
        chosenImageView.setDrawingCacheEnabled(true);

        loadChosenImageButton = (Button)findViewById(R.id.manualLoadImageButton);

        loadChosenImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });

        normalName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    normalName.setHint("");
                } else {
                    normalName.setHint("Name");
                }
            }
        });

        generalName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    generalName.setHint("");
                } else {
                    generalName.setHint("Name");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            imageURL = data.getData().toString();
            Picasso.with(this).load(data.getData()).into(chosenImageView);
        }
    }

    private void addIngredient() {
        Log.i(normalName.getText().toString(), normalName.getText().toString());
        if (normalName.getText().length() == 0) {
            Toast.makeText(this, "You must put some name of an ingredient!", Toast.LENGTH_LONG).show();
        } else {
            String nameOfIngredient = normalName.getText().toString();
            Ingredient toAdd = new Ingredient();
            toAdd.setIngredientName(nameOfIngredient);

            EditText general = (EditText)findViewById(R.id.manualGeneralTextView);
            String generalNameOfIngredient = general.getText().toString();
            toAdd.setIngredientGeneralName(generalNameOfIngredient);

            toAdd.setIngredientId(0);

            toAdd.setIngredientImageBytes("none"); // as a default
            if (chosenImageView != null) {

                chosenImageView.buildDrawingCache();
                Bitmap bitmap = chosenImageView.getDrawingCache();
                if (bitmap != null) {
                    Log.i("not null", "not null");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] bytes = baos.toByteArray();
                    String base64 = null;
                    base64 = Base64.encodeToString(bytes, 0);

                    toAdd.setIngredientImageBytes(base64);
                }
            }

            toAdd.setIngredientImageURL(imageURL);

            DataAccessLayer dal = new DataAccessLayer(this);
            dal.storeIngredient(toAdd);

            finish();
        }
    }

}
