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
    ArrayList<String> checked = new ArrayList<>();
    // ArrayList<HashMap<String, Integer>> customAdapter= new ArrayList<>();

    ArrayList<CategoryData> categoryData = new ArrayList<>();

    private static LayoutInflater inflater=null;
    public CustomAdapter(MainActivity mainActivity, String[] osNameList, int[] osImages, int[] osImagesHover, ArrayList<String> checked) {
        // TODO Auto-generated constructor stub
        result=osNameList;
        context=mainActivity;
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

    public class Holder
    {
        TextView os_text;
        ImageView os_img;
        Boolean selection = false;
        String name;
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
        // holder.selection = checked.contains(holder.name);
        holder.selection = checked.contains(holder.name);

        if (checked.contains(holder.name)) {
            holder.os_img.setImageResource(hoverId[position]);
            categoryData.add(new CategoryData(holder.name, position));
        }

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