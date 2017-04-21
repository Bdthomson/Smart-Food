package com.schachte.android.proactive_food_app.models;


public class PedometerEntry {

    int dbRow;
    String dateTime;
    float totalSteps;
    float stepsSinceReset;

    public PedometerEntry(int dbRow, String dateTime, float totalSteps, float stepsSinceReset) {
        this.dbRow = dbRow;
        this.dateTime = dateTime;
        this.totalSteps = totalSteps;
        this.stepsSinceReset = stepsSinceReset;
    }

    public int getDbRow() {
        return dbRow;
    }

    public String getDateTime() {
        return dateTime;
    }

    public float getTotalSteps() {
        return totalSteps;
    }

    public float getStepsSinceReset() {
        return stepsSinceReset;
    }

    @Override
    public String toString() {
        return "" + getDbRow() + "\t" + getDateTime() + "\t" + getTotalSteps() + "\t" + getStepsSinceReset();
    }
}
