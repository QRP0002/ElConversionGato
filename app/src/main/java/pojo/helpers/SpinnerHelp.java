package pojo.helpers;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import pojo.database.ConversionDBHelper;

public class SpinnerHelp {
    private ArrayList<String> types, topSpinnerTypes;
    private static SpinnerHelp instance = null;

    private SpinnerHelp() {}

    public static SpinnerHelp getInstance() {
        if(instance == null) {
            instance = new SpinnerHelp();
        }
        return instance;
    }

    public ArrayList<String> updateSpinnerOne(String conversionType, Context context) {
        ConversionDBHelper db = new ConversionDBHelper(context);
        this.types = db.getFromSpinnerData(conversionType.toLowerCase());
        return this.types;
    }

    public ArrayList<String> updateSpinnerTwo(String removeFrom) {
        ArrayList<String> returnArray = new ArrayList<>();

        if(!returnArray.isEmpty() && returnArray.size() > 0) {
            returnArray.clear();
        }

        for(String tempStr : this.types) {
            if(!tempStr.equals(removeFrom)) {
                returnArray.add(tempStr);
            }
        }
        Log.d("STRING ARRAY", returnArray.toString());
        return(returnArray);
    }

    public ArrayList<String> initialSpinnerTwo() {
        return updateSpinnerTwo(this.types.get(0));
    }

    public ArrayList<String> getTopSpinnerTypes() {
        return topSpinnerTypes;
    }

    public void setTopSpinnerTypes(ArrayList<String> topSpinnerTypes) {
        this.topSpinnerTypes = topSpinnerTypes;
    }
}
