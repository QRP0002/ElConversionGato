package pojo.convert;

import android.content.Context;

import pojo.database.ConversionDBHelper;

public class ConversionNoFormula extends Conversions {
    private Context context;

    public ConversionNoFormula(Context context) {
        this.context = context;
    }

    @Override
    public String convert(double valueIn) {
        return(super.appendType(Double.toString(calculate(valueIn))));
    }

    private double calculate(double valueIn) {
        ConversionDBHelper db = new ConversionDBHelper(this.context);
        double convertValue = db.getNoFormulaValue(super.getConversionType(),
                super.getConversionTypeTwo());
        return valueIn * convertValue;
    }
}
