package com.schachte.android.proactive_food_app;



public class CategoryData {
    public final String catName;
    public final int position;

    /**
     * This class is used for storing the location of the selected preferred cusine categories
     * It also stores whether or not they are checked at a given time to update shared prefs.
     *
     * @param catName the selected category name at a given instance
     * @param position The index that the currently selected item resides in within the OS image list
     */
    public CategoryData(String catName, int position) {
        this.catName = catName;
        this.position = position;
    }
}
