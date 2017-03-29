package com.schachte.android.proactive_food_app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class CustomAdapter extends BaseAdapter{

    String [] result;
    Context context;
    int [] imageId;
    int [] hoverId;
    public final String TAG = this.getClass().getSimpleName();
    // ArrayList<HashMap<String, Integer>> customAdapter= new ArrayList<>();

    ArrayList<CategoryData> categoryData = new ArrayList<>();

    private static LayoutInflater inflater=null;
    public CustomAdapter(MainActivity mainActivity, String[] osNameList, int[] osImages, int[] osImagesHover) {
        // TODO Auto-generated constructor stub
        result=osNameList;
        context=mainActivity;
        imageId=osImages;
        hoverId=osImagesHover;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView os_text;
        ImageView os_img;
        Boolean selection = false;
        String name;
    }

    /**
     * Given a stringified JSON array, this sets imageViews to checked.
     * @param stringifiedArray The stringified JSON Array of Categories that are checked.
     */
    public void setChecked(String stringifiedArray){
        JSONArray array = null;
        ArrayList<String> strings = new ArrayList<>();
        try {
            array = new JSONArray(stringifiedArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Loop through strings in the json array;
        for(int i = 0; i < array.length(); i++){
            try {
                strings.add(i, (String) array.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.d(TAG, "Printing list from loaded from preferences, after loading to string array.");
        for(String data : strings){
            Log.d(TAG, data);
        }

        // Check each imageView and check if names are found in data list.

        // If an item is not found in list of strings, remove it to category data.

        boolean found = false;

        // For each imageView
        for(CategoryData d : categoryData){

            // For each checked category
            for(String s : strings){

                // If that category is found, set found to true.
                if (d.catName.compareTo(s) == 0){
                    found = true;
                    break;
                }
            }

            // If the category was not found in the preferences, remove it from checked.
            if(!found){
                categoryData.remove(d);
            }

            found = false;
        }
    }

    public ArrayList<CategoryData> getSelectedCategories() {
        return this.categoryData;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        final View rowView;

        rowView = inflater.inflate(R.layout.grid_layout, null);
        holder.os_img =(ImageView) rowView.findViewById(R.id.os_images);
        holder.os_img.setImageResource(imageId[position]);
        holder.name = result[position];

        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_SHORT).show();

                if (!holder.selection) {
                    holder.selection = true;
                    holder.os_img.setImageResource(hoverId[position]);

                    categoryData.add(new CategoryData(holder.name, position));


                } else {
                    holder.selection = false;
                    holder.os_img.setImageResource(imageId[position]);

                    for(CategoryData category : categoryData)
                    {
                        if (category.position == position)
                        {
                            categoryData.remove(category);
                            break;
                        }
                    }
                }
            }
        });

        return rowView;
    }

}