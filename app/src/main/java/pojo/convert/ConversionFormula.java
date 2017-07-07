package pojo.convert;

import com.fathzer.soft.javaluator.DoubleEvaluator;

public class ConversionFormula extends Conversions {
    private String formula;

    public ConversionFormula(String formula) {
        this.formula = formula;
    }

    @Override
    public String convert(double valueIn) {
        return(super.appendType(Double.toString(calculate(valueIn))));
    }

    private double calculate(double valueIn) {
        DoubleEvaluator evaluator = new DoubleEvaluator();
        return evaluator.evaluate(parse(valueIn));
    }

    private String parse(double valueIn) {
        StringBuffer sb = new StringBuffer(this.formula);
        for (int i = 0; i < formula.length(); i++) {
            if(this.formula.charAt(i) == 'x') {
                sb.deleteCharAt(i);
                sb.insert(i, valueIn);
                break;
            }
        }
        return (sb.toString());
    }
}
