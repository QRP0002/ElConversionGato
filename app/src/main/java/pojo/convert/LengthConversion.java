package pojo.convert;

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
        return(Double.toString(calculate(valueIn)));
    }

    private double calculate(double valueIn) {
        switch (super.getConversionType()) {
            case "mi":
                return miles.get(super.getConversionTypeTwo()) * valueIn;
            case "ft":
                return feet.get(super.getConversionTypeTwo()) * valueIn;
            case "in":
                return inches.get(super.getConversionTypeTwo()) * valueIn;
            case "m":
                return meters.get(super.getConversionTypeTwo()) * valueIn;
            case "cm":
                return centimeters.get(super.getConversionTypeTwo()) * valueIn;
            case "km":
                return kilometers.get(super.getConversionTypeTwo()) * valueIn;
            default:
                return 0.0;
        }
    }

    private void fillMaps() {
        switch (super.getConversionType()) {
            case "mi":
                if(miles.isEmpty()){
                    miles.put("ft", 5280.0);
                    miles.put("in", 63360.0);
                    miles.put("m", 1609.34);
                    miles.put("cm", 160934.0);
                    miles.put("km", 1.60934);
                }
                break;
            case  "ft":
                if(feet.isEmpty()) {
                    feet.put("mi", 0.000189394);
                    feet.put("in", 12.0);
                    feet.put("m", 0.3048);
                    feet.put("cm", 30.48);
                    feet.put("km", 0.0003048);
                }
                break;
            case "in":
                if(inches.isEmpty()) {
                    inches.put("ft", 0.0833333);
                    inches.put("mi", 0.00001578);
                    inches.put("m", 0.0254);
                    inches.put("cm", 2.54);
                    inches.put("km", 0.0000254);
                }
                break;
            case "m":
                if(meters.isEmpty()) {
                    meters.put("ft", 3.28084);
                    meters.put("in", 39.3701);
                    meters.put("mi", 0.000621371);
                    meters.put("cm", 100.0);
                    meters.put("km", 0.001);
                }
                break;
            case "cm":
                if(centimeters.isEmpty()) {
                    centimeters.put("ft", 0.032808);
                    centimeters.put("in", 63360.0);
                    centimeters.put("mi", 1609.34);
                    centimeters.put("m",160934.0);
                    centimeters.put("km", 1.60934);
                }
                break;
            case "km":
                if(kilometers.isEmpty()) {
                    kilometers.put("ft", 5280.0);
                    kilometers.put("in", 63360.0);
                    kilometers.put("mi", 1609.34);
                    kilometers.put("cm", 160934.0);
                    kilometers.put("m", 1.60934);
                }
                break;
            default:
                break;
        }
    }
}
