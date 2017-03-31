package pojo.convert;

import android.util.Log;

import java.util.HashMap;

public class TemperatureConversion extends Conversions {
    private HashMap<String, String> far = new HashMap<>();
    private HashMap<String, String> cel = new HashMap<>();
    private HashMap<String, String> kel = new HashMap<>();

    @Override
    public String convert(double speedIn) {
        fillMap();
        return (Double.toString(calculate(speedIn)));
    }

    private double calculate(double valueIn) {
        double output = 0.0;
        switch (super.getConversionType()) {
            case "far":
                parse(valueIn, 'f', far.get(super.getConversionTypeTwo()));
                break;
            case "cel":
                parse(valueIn, 'c', cel.get(super.getConversionTypeTwo()));
                break;
            case "kel":
                parse(valueIn, 'k', kel.get(super.getConversionTypeTwo()));
                break;
            default:
                break;
        }
        return output;
    }

    private String parse(double valueIn, char letter, String formula) {
        StringBuffer sb = new StringBuffer(formula);
        for (int i = 0; i < formula.length(); i++) {
            if(formula.charAt(i) == letter) {
                sb.insert(i, valueIn);
            }
        }
        Log.d("TESTINGSB", sb.toString());
        return (sb.toString());
    }

    private void fillMap() {
        switch (super.getConversionType()) {
            case "far":
                if(far.isEmpty()){
                    far.put("cel", "(((f - 32) * 5) / 9)");
                    far.put("kel", "((((f – 32) * 5) / 9) + 273.15)");
                }
                break;
            case  "cel":
                if(cel.isEmpty()) {
                    cel.put("far", "(((c * 9) / 5) + 32)");
                    cel.put("kel", "(c + 273.15)");
                }
                break;
            case "kel":
                if(kel.isEmpty()) {
                    kel.put("far", "(((k * 9) / 5) - 459.67)");
                    kel.put("cel", "(k - 273.15)");
                }
                break;
            default:
                break;
        }
    }
}
