package pojo.helpers;

import android.util.Log;

import java.util.ArrayList;

public class SpinnerHelp {
    private ArrayList<String> types, parsedTypes;
    private String input;

    public SpinnerHelp(ArrayList<String> types) {
        super();
        this.types = types;
    }

    private void setParsedTypes(ArrayList<String> parsedArray) {
        this.parsedTypes = parsedArray;
    }

    public ArrayList<String> getParsedTypes() {
        return this.parsedTypes;
    }

    public void update(String input) {
        parse(input);
    }

    private void parse(String removeFrom) {
        ArrayList<String> returnArray = new ArrayList<>();
        returnArray.clear();

        for(String tempStr : this.types) {
            if(!tempStr.equals(removeFrom)) {
                returnArray.add(tempStr);
            }
        }
        setParsedTypes(returnArray);
    }
}
