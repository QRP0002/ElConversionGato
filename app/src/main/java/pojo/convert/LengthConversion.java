package pojo.convert;

import java.util.HashMap;

public class LengthConversion extends Conversions {
    private HashMap<String, Double> miles = new HashMap<>();
    private HashMap<String, Double> feet = new HashMap<>();
    private HashMap<String, Double> inches = new HashMap<>();
    private HashMap<String, Double> meters = new HashMap<>();
    private HashMap<String, Double> centimeters = new HashMap<>();
    private HashMap<String, Double> kilometers = new HashMap<>();

    private String formula;

    @Override
    public String convert(double valueIn) {
        fillMaps();
        double[] calculation = calculate(valueIn);
        setFormula(Double.toString(calculation[0]));
        return(Double.toString(calculation[1]));
    }

    private double[] calculate(double valueIn) {
        double multiplyVariable = 0.0;
        switch (super.getConversionType()) {
            case "mi":
                multiplyVariable = miles.get(super.getConversionTypeTwo());
                break;
            case "ft":
                multiplyVariable = feet.get(super.getConversionTypeTwo());
                break;
            case "in":
                multiplyVariable = inches.get(super.getConversionTypeTwo());
                break;
            case "m":
                multiplyVariable = meters.get(super.getConversionTypeTwo());
                break;
            case "cm":
                multiplyVariable = centimeters.get(super.getConversionTypeTwo());
                break;
            case "km":
                multiplyVariable = kilometers.get(super.getConversionTypeTwo());
                break;
            default:
                break;
        }
        double[] output = {multiplyVariable, multiplyVariable * valueIn};
        return(output);
    }

    public String getFormula() {
        return this.formula;
    }

    private void setFormula(String number) {
        this.formula = super.getConversionTypeTwo() + " = "
                + super.getConversionType()+ " * " + number;
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
                    miles.put("mi", 1.0);
                }
                break;
            case  "ft":
                if(feet.isEmpty()) {
                    feet.put("mi", 0.000189394);
                    feet.put("in", 12.0);
                    feet.put("m", 0.3048);
                    feet.put("cm", 30.48);
                    feet.put("km", 0.0003048);
                    feet.put("ft", 1.0);
                }
                break;
            case "in":
                if(inches.isEmpty()) {
                    inches.put("ft", 0.0833333);
                    inches.put("mi", 0.00001578);
                    inches.put("m", 0.0254);
                    inches.put("cm", 2.54);
                    inches.put("km", 0.0000254);
                    inches.put("in", 1.0);
                }
                break;
            case "m":
                if(meters.isEmpty()) {
                    meters.put("ft", 3.28084);
                    meters.put("in", 39.3701);
                    meters.put("mi", 0.000621371);
                    meters.put("cm", 100.0);
                    meters.put("km", 0.001);
                    meters.put("m", 1.0);
                }
                break;
            case "cm":
                if(centimeters.isEmpty()) {
                    centimeters.put("ft", 0.032808);
                    centimeters.put("in", 63360.0);
                    centimeters.put("mi", 1609.34);
                    centimeters.put("m",160934.0);
                    centimeters.put("km", 1.60934);
                    centimeters.put("cm", 1.0);
                }
                break;
            case "km":
                if(kilometers.isEmpty()) {
                    kilometers.put("ft", 5280.0);
                    kilometers.put("in", 63360.0);
                    kilometers.put("mi", 1609.34);
                    kilometers.put("cm", 160934.0);
                    kilometers.put("m", 1.60934);
                    kilometers.put("km", 1.0);
                }
                break;
            default:
                break;
        }
    }
}
