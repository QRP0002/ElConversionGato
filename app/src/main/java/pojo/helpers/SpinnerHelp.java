package pojo.helpers;

import java.util.ArrayList;

public class SpinnerHelp {
    private ArrayList<String> types;
    private static SpinnerHelp instance = null;

    private SpinnerHelp() {}

    public static SpinnerHelp getInstance() {
        if(instance == null) {
            instance = new SpinnerHelp();
        }
        return instance;
    }

    public ArrayList<String> updateSpinnerOne(String conversionType) {
        ArrayList<String> sendArray = new ArrayList<>();
        switch (conversionType) {
            case "Length":
                sendArray.add("feet");
                sendArray.add("miles");
                sendArray.add("inches");
                sendArray.add("meters");
                sendArray.add("centimeters");
                sendArray.add("kilometers");
                this.types = sendArray;
                return sendArray;
            case "Mass":
                sendArray.add("pounds");
                sendArray.add("ounces");
                sendArray.add("grams");
                sendArray.add("kilograms");
                sendArray.add("milligrams");
                this.types = sendArray;
                return sendArray;
            case "Speed":
                sendArray.add("mph");
                sendArray.add("km/h");
                sendArray.add("knots");
                this.types = sendArray;
                return sendArray;
            case "Temperature":
                sendArray.add("\u2109"); //Far
                sendArray.add("\u2103"); //Cel
                sendArray.add("\u212A"); //Kel
                this.types = sendArray;

                return sendArray;
            default:
                return null;
        }
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
        return(returnArray);
    }

    public ArrayList<String> initialSpinnerTwo() {
        return updateSpinnerTwo(this.types.get(0));
    }
}
