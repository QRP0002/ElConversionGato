package pojo.convert;

import java.text.DecimalFormat;
import java.util.HashMap;

public class MassConversion extends Conversions {
    private HashMap<String, Double> pound = new HashMap<>();
    private HashMap<String, Double> ounces = new HashMap<>();
    private HashMap<String, Double> grams = new HashMap<>();
    private HashMap<String, Double> kilograms = new HashMap<>();
    private HashMap<String, Double> milligrams = new HashMap<>();

    @Override
    public String convert(double valueIn) {
        fillMaps();
        return(super.appendType(Double.toString(calculate(valueIn))));
    }

    private double calculate(double valueIn) {
        switch (super.getConversionType()) {
            case "pounds":
                return pound.get(super.getConversionTypeTwo()) * valueIn;
            case "ounces":
                return ounces.get(super.getConversionTypeTwo()) * valueIn;
            case "grams":
                return grams.get(super.getConversionTypeTwo()) * valueIn;
            case "kilograms":
                return kilograms.get(super.getConversionTypeTwo()) * valueIn;
            case "milligrams":
                return milligrams.get(super.getConversionTypeTwo()) * valueIn;
            default:
                return 0.0;
        }
    }

    private void fillMaps() {
        switch (super.getConversionType()) {
            case "pounds":
                if(pound.isEmpty()){
                    pound.put("ounces", 16.0);
                    pound.put("grams", 453.592);
                    pound.put("kilograms", .0453592);
                    pound.put("milligrams", 453592.0);
                }
                break;
            case  "ounces":
                if(ounces.isEmpty()) {
                    ounces.put("pounds", 0.0625);
                    ounces.put("grams", 28.3495);
                    ounces.put("kilograms", 0.0283495);
                    ounces.put("milligrams", 28349.5);
                }
                break;
            case "grams":
                if(grams.isEmpty()) {
                    grams.put("pounds", 0.00220462);
                    grams.put("ounces", 0.035274);
                    grams.put("kilograms", 0.001);
                    grams.put("milligrams", 1000.0);
                }
                break;
            case "kilograms":
                if(kilograms.isEmpty()) {
                    kilograms.put("pounds", 2.20462);
                    kilograms.put("ounces", 35.274);
                    kilograms.put("grams", 1000.0);
                    kilograms.put("milligrams", 100000.0);
                }
                break;
            case "milligrams":
                if(milligrams.isEmpty()) {
                    milligrams.put("pounds", 0.00000202246);
                    milligrams.put("ounces", 0.000035274);
                    milligrams.put("grams", 0.001);
                    milligrams.put("kilograms", 0.000001);
                }
                break;
            default:
                break;
        }
    }
}
