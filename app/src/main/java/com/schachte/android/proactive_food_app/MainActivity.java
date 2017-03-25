package com.schachte.android.proactive_food_app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    GridView gridview;

    ArrayList<CategoryData> selectedCategories;

    public final String TAG = this.getClass().getSimpleName();


    public static String[] osNameList = {
            "American Food",
            "Chinese Food",
    };
    public static int[] osImages = {
            R.mipmap.america,
            R.mipmap.chinese,
    };

    public static int[] osImagesHover = {
            R.mipmap.american_hover,
            R.mipmap.chinese_hover,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button saveCategories = (Button) findViewById(R.id.save_categories);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridview = (GridView) findViewById(R.id.customgrid);
        final CustomAdapter categoryData = new CustomAdapter(this, osNameList, osImages, osImagesHover);
        gridview.setAdapter(categoryData);

        final Button button = (Button) findViewById(R.id.save_categories);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                selectedCategories = categoryData.getSelectedCategories();

                for (CategoryData data : selectedCategories) {
                    Log.d(TAG, data.catName);
                }
            }
        });


    }

}
