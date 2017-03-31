package pojo.convert;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import java.util.HashMap;

public class TemperatureConversion extends Conversions {
    private HashMap<String, String> far = new HashMap<>();
    private HashMap<String, String> cel = new HashMap<>();
    private HashMap<String, String> kel = new HashMap<>();

    @Override
    public String convert(double valueIn) {
        fillMap();
        return(super.appendType(Double.toString(calculate(valueIn))));
    }

    private double calculate(double valueIn) {
        DoubleEvaluator evaluator = new DoubleEvaluator();
        switch (super.getConversionType()) {
            case "\u2109":
                return(evaluator.evaluate(parse(valueIn,
                        'f', far.get(super.getConversionTypeTwo()))));
            case "\u2103":
                return(evaluator.evaluate(parse(valueIn,
                        'c', cel.get(super.getConversionTypeTwo()))));
            case "\u212A":
                return(evaluator.evaluate(parse(valueIn,
                        'k', kel.get(super.getConversionTypeTwo()))));
            default:
                return 0.0;
        }
    }

    private String parse(double valueIn, char letter, String formula) {
        StringBuffer sb = new StringBuffer(formula);
        for (int i = 0; i < formula.length(); i++) {
            if(formula.charAt(i) == letter) {
                sb.deleteCharAt(i);
                sb.insert(i, valueIn);
            }
        }
        return (sb.toString());
    }

    private void fillMap() {
        switch (super.getConversionType()) {
            case "\u2109":
                if(far.isEmpty()){
                    far.put("\u2103", "(((f - 32.0) * 5) / 9)");
                    far.put("\u212A", "((((f - 32.0) * 5) / 9) + 273.15)");
                }
                break;
            case  "\u2103":
                if(cel.isEmpty()) {
                    cel.put("\u2109", "(((c * 9) / 5) + 32.0)");
                    cel.put("\u212A", "(c + 273.15)");
                }
                break;
            case "\u212A":
                if(kel.isEmpty()) {
                    kel.put("\u2109", "(((k * 9) / 5) - 459.67)");
                    kel.put("\u2103", "(k - 273.15)");
                }
                break;
            default:
                break;
        }
    }
}
