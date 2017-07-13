package pojo.convert;

import android.icu.math.BigDecimal;
import android.util.Log;

public class ConversionNoFormula extends Conversions {
    private double dataBaseValue;

    public ConversionNoFormula(double dataBaseValue) {
        this.dataBaseValue = dataBaseValue;
    }

    @Override
    public String convert(double valueIn) {
        return(super.appendType((calculate(valueIn)).toString()));
    }

    private BigDecimal calculate(double valueIn) {
        BigDecimal userValue = BigDecimal.valueOf(valueIn);
        BigDecimal dbValue = BigDecimal.valueOf(dataBaseValue);
        Log.d("NoFormula", userValue.multiply(dbValue).toString());
        return userValue.multiply(dbValue);
    }
}