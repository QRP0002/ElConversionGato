package pojo.convert;

import java.util.HashMap;

public class SpeedConversion extends Conversions {
    private HashMap<String, Double> miles = new HashMap<>();
    private HashMap<String, Double> meters = new HashMap<>();
    private HashMap<String, Double> knots = new HashMap<>();

    @Override
    public String convert(double speedIn) {
        fillMap();
        return (Double.toString(calculate(speedIn)));
    }

    private double calculate(double valueIn) {
        switch (super.getConversionType()) {
            case "lb":
                return miles.get(super.getConversionTypeTwo()) * valueIn;
            case "oz":
                return meters.get(super.getConversionTypeTwo()) * valueIn;
            case "g":
                return knots.get(super.getConversionTypeTwo()) * valueIn;
            default:
                return 0.0;
        }
    }

    private void fillMap() {
        switch (super.getConversionType()) {
            case "mph":
                if(miles.isEmpty()){
                    miles.put("kph", 1.60934);
                    miles.put("knots", 0.868976);
                }
                break;
            case  "kph":
                if(meters.isEmpty()) {
                    meters.put("mph", 0.621371);
                    meters.put("knots", 0.539957);
                }
                break;
            case "knots":
                if(knots.isEmpty()) {
                    knots.put("mph", 1.15078);
                    knots.put("kph", 1.852);
                }
                break;
            default:
                break;
        }
    }
}
