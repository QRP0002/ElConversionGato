package pojo.convert;

import android.util.Log;

import java.text.DecimalFormat;
import java.util.HashMap;

public class LengthConversion extends Conversions {
    private HashMap<String, Double> miles = new HashMap<>();
    private HashMap<String, Double> feet = new HashMap<>();
    private HashMap<String, Double> inches = new HashMap<>();
    private HashMap<String, Double> meters = new HashMap<>();
    private HashMap<String, Double> centimeters = new HashMap<>();
    private HashMap<String, Double> kilometers = new HashMap<>();

    @Override
    public String convert(double valueIn) {
        fillMaps();
        return(super.appendType(Double.toString(calculate(valueIn))));
    }

    private double calculate(double valueIn) {
        switch (super.getConversionType()) {
            case "miles":
                return miles.get(super.getConversionTypeTwo()) * valueIn;
            case "feet":
                return feet.get(super.getConversionTypeTwo()) * valueIn;
            case "inches":
                return inches.get(super.getConversionTypeTwo()) * valueIn;
            case "meters":
                return meters.get(super.getConversionTypeTwo()) * valueIn;
            case "centimeters":
                return centimeters.get(super.getConversionTypeTwo()) * valueIn;
            case "kilometers":
                return kilometers.get(super.getConversionTypeTwo()) * valueIn;
            default:
                return 0.0;
        }
    }

    private void fillMaps() {
        switch (super.getConversionType()) {
            case "miles":
                if(miles.isEmpty()){
                    miles.put("feet", 5280.0);
                    miles.put("inches", 63360.0);
                    miles.put("meters", 1609.34);
                    miles.put("centimeters", 160934.0);
                    miles.put("kilometers", 1.60934);
                }
                break;
            case  "feet":
                if(feet.isEmpty()) {
                    feet.put("miles", 0.000189394);
                    feet.put("inches", 12.0);
                    feet.put("meters", 0.3048);
                    feet.put("centimeters", 30.48);
                    feet.put("kilometers", 0.0003048);
                }
                break;
            case "in":
                if(inches.isEmpty()) {
                    inches.put("feet", 0.0833333);
                    inches.put("miles", 0.00001578);
                    inches.put("meters", 0.0254);
                    inches.put("centimeters", 2.54);
                    inches.put("kilometers", 0.0000254);
                }
                break;
            case "meters":
                if(meters.isEmpty()) {
                    meters.put("feet", 3.28084);
                    meters.put("inches", 39.3701);
                    meters.put("miles", 0.000621371);
                    meters.put("centimeters", 100.0);
                    meters.put("kilometers", 0.001);
                }
                break;
            case "centimeters":
                if(centimeters.isEmpty()) {
                    centimeters.put("feet", 0.032808);
                    centimeters.put("inches", 63360.0);
                    centimeters.put("miles", 1609.34);
                    centimeters.put("meters",160934.0);
                    centimeters.put("kilometers", 1.60934);
                }
                break;
            case "kilometers":
                if(kilometers.isEmpty()) {
                    kilometers.put("feet", 5280.0);
                    kilometers.put("inches", 63360.0);
                    kilometers.put("miles", 1609.34);
                    kilometers.put("centimeters", 160934.0);
                    kilometers.put("meters", 1.60934);
                }
                break;
            default:
                break;
        }
    }
}
