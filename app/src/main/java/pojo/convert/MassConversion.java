package pojo.convert;

import java.util.HashMap;

import pojo.convert.Conversions;

public class MassConversion extends Conversions {
    private HashMap<String, Double> pound = new HashMap<>();
    private HashMap<String, Double> ounces = new HashMap<>();
    private HashMap<String, Double> grams = new HashMap<>();
    private HashMap<String, Double> kilograms = new HashMap<>();
    private HashMap<String, Double> milligrams = new HashMap<>();

    private final static String TAG = "LENGTH CONVERSION TAG";

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
            case "lb":
                multiplyVariable = pound.get(super.getConversionTypeTwo());
                break;
            case "oz":
                multiplyVariable = ounces.get(super.getConversionTypeTwo());
                break;
            case "g":
                multiplyVariable = grams.get(super.getConversionTypeTwo());
                break;
            case "kg":
                multiplyVariable = kilograms.get(super.getConversionTypeTwo());
                break;
            case "mg":
                multiplyVariable = milligrams.get(super.getConversionTypeTwo());
                break;
            default:
                break;
        }
        double[] output = {multiplyVariable, multiplyVariable * valueIn};
        return(output);
    }

    private void setFormula(String number) {
        this.formula = super.getConversionTypeTwo() + " = "
                + super.getConversionType()+ " * " + number;
    }

    public String getFormula() {
        return this.formula;
    }

    private void fillMaps() {
        switch (super.getConversionType()) {
            case "lb":
                if(pound.isEmpty()){
                    pound.put("oz", 16.0);
                    pound.put("g", 453.592);
                    pound.put("kg", .0453592);
                    pound.put("mg", 453592.0);
                    pound.put("lb", 1.0);
                }
                break;
            case  "oz":
                if(ounces.isEmpty()) {
                    ounces.put("lb", 0.0625);
                    ounces.put("g", 28.3495);
                    ounces.put("kg", 0.0283495);
                    ounces.put("mg", 28349.5);
                    ounces.put("oz", 1.0);
                }
                break;
            case "g":
                if(grams.isEmpty()) {
                    grams.put("lb", 0.00220462);
                    grams.put("oz", 0.035274);
                    grams.put("kg", 0.001);
                    grams.put("mg", 1000.0);
                    grams.put("g", 1.0);
                }
                break;
            case "kg":
                if(kilograms.isEmpty()) {
                    kilograms.put("lb", 2.20462);
                    kilograms.put("oz", 35.274);
                    kilograms.put("g", 1000.0);
                    kilograms.put("mg", 100000.0);
                    kilograms.put("kg", 1.0);
                }
                break;
            case "mg":
                if(milligrams.isEmpty()) {
                    milligrams.put("lb", 0.00000202246);
                    milligrams.put("oz", 0.000035274);
                    milligrams.put("g", 0.001);
                    milligrams.put("kg", 0.000001);
                    milligrams.put("mg", 1.0);
                }
                break;
            default:
                break;
        }
    }
}
