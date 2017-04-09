package com.schachte.android.proactive_food_app.activities.recipe_list_activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.schachte.android.proactive_food_app.R;

/**
 * Dialog box that will pop up and allow the user to choose which meal they would like to
 * find recipes for.  The first time this is opened it will default to the next meal
 * determined by the time of day.
 *
 * Created by Spencer Smith on 4/8/2017.
 */

public class MealDialog extends DialogFragment {

    public enum Meal { Breakfast, Lunch, Dinner }

    private Meal mealToDefault; //0 = Breakfast, 1 = Lunch, 2 = Dinner

    public void setMealToDefault(Meal defaultMeal) {
        this.mealToDefault = defaultMeal;
    }

    /*
     * Used to pass the data back to the activity that called this.  The activity will
     * implement this interface and capture the data from the method when we call
     * to it.
     */
    public interface MealDialogListener {
        void onChangeMeal(Meal mealToSearch);
    }
    private MealDialogListener listener;

    /*
     * Set the listener to point to the activity that called us so that we can return the data
     * to it
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (MealDialogListener) context;
    }

    /*
     * This will create the actually fragment that will pop up and give the user options. Whenever
     * and option is chosen the activity that called us will be notified.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Sets the title and radio options
        builder.setTitle("What meal would you like recipes for?")
                .setSingleChoiceItems(R.array.meal_options, mealToDefault.ordinal(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onChangeMeal(Meal.values()[which]);
                    }
                });

        //Adds an ok button so that the user can close the dialog
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }
}
