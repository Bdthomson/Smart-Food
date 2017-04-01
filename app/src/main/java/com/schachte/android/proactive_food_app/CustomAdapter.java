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

    //Context reference
    Context context;

    //Contains references to all the cuisine images
    int [] imageId;

    //Contains a 1:1 mapped reference to all the hover-over images to above
    int [] hoverId;

    //Debugging tag
    public final String TAG = this.getClass().getSimpleName();
    ArrayList<String> checked = new ArrayList<>();
    ArrayList<CategoryData> categoryData = new ArrayList<>();
    private static LayoutInflater inflater=null;

    /**
     *
     *  Custom adapter is used to retrieve and display the images and handle user selection
     *  for shared preferences
     *
     *  @param category The reference to the previous activity calling the adapter
     *  @param osNameList String names for cuisine cateogories
     *  @param osImages Regular image references for image resource (cuisine cateogory images)
     *  @param osImagesHover Hover image references for image resource (cuisine cateogory images)
     *  @param checked Arraylist used to store the checked objects into users phone memory
     **/
    public CustomAdapter(Category category, String[] osNameList, int[] osImages, int[] osImagesHover, ArrayList<String> checked) {
        // TODO Auto-generated constructor stub
        result=osNameList;
        context=category;
        imageId=osImages;
        hoverId=osImagesHover;
        this.checked=checked;
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

    /*
        Simple class used to hold the image data that is stored for user-pref references
     */
    public class Holder
    {
        ImageView os_img;
        Boolean selection = false;
        String name;
    }

    /**
     *
     * @return CategoryData categoryData object containing the selected cuisine choices
     */
    public ArrayList<CategoryData> getSelectedCategories() {
        return this.categoryData;
    }


    /**
     * Function used to update and remove selected items and store them in shared prefs. (Phone memory)
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        final View rowView;

        rowView = inflater.inflate(R.layout.grid_layout, null);
        holder.os_img =(ImageView) rowView.findViewById(R.id.os_images);
        holder.os_img.setImageResource(imageId[position]);
        holder.name = result[position];
        holder.selection = checked.contains(holder.name);

        if (checked.contains(holder.name)) {
            Log.d(TAG, "True for " + holder.name);
            holder.os_img.setImageResource(hoverId[position]);
            categoryData.add(new CategoryData(holder.name, position));
        }

        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //If an item isn't selected, then select it, add to array list and store in shared prefs
                if (!holder.selection) {
                    holder.selection = true;
                    holder.os_img.setImageResource(hoverId[position]);
                    categoryData.add(new CategoryData(holder.name, position));
                } else {
                    //If an item is selected, remove it and get rid of it from shared preferences
                    holder.selection = false;
                    holder.os_img.setImageResource(imageId[position]);
                    for(CategoryData category : categoryData) {
                        if (category.position == position) {
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