package pojo.convert;

import java.util.HashMap;

public class SpeedConversion extends Conversions {
    private HashMap<String, Double> miles = new HashMap<>();
    private HashMap<String, Double> meters = new HashMap<>();
    private HashMap<String, Double> knots = new HashMap<>();

    @Override
    public String convert(double valueIn) {
        fillMap();
        return(super.appendType(Double.toString(calculate(valueIn))));
    }

    private double calculate(double valueIn) {
        switch (super.getConversionType()) {
            case "mph":
                return miles.get(super.getConversionTypeTwo()) * valueIn;
            case "km/h":
                return meters.get(super.getConversionTypeTwo()) * valueIn;
            case "knots":
                return knots.get(super.getConversionTypeTwo()) * valueIn;
            default:
                return 0.0;
        }
    }

    private void fillMap() {
        switch (super.getConversionType()) {
            case "mph":
                if(miles.isEmpty()){
                    miles.put("km/h", 1.60934);
                    miles.put("knots", 0.868976);
                }
                break;
            case  "km/h":
                if(meters.isEmpty()) {
                    meters.put("mph", 0.621371);
                    meters.put("knots", 0.539957);
                }
                break;
            case "knots":
                if(knots.isEmpty()) {
                    knots.put("mph", 1.15078);
                    knots.put("km/h", 1.852);
                }
                break;
            default:
                break;
        }
    }
}
