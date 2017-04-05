package pojo.convert;

import android.content.Context;
import com.fathzer.soft.javaluator.DoubleEvaluator;

import pojo.database.ConversionDBHelper;

public class ConversionFormula extends Conversions {
    private Context context;

    public ConversionFormula(Context context) {
        this.context = context;
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
        ConversionDBHelper db = new ConversionDBHelper(this.context);
        String formula = db.getFormulaValue(super.getConversionType(),
                super.getConversionTypeTwo());

        StringBuffer sb = new StringBuffer(formula);
        for (int i = 0; i < formula.length(); i++) {
            if(formula.charAt(i) == 'x') {
                sb.deleteCharAt(i);
                sb.insert(i, valueIn);
                break;
            }
        }
        return (sb.toString());
    }
}
