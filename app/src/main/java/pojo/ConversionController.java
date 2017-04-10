package pojo;

import android.content.Context;

import pojo.convert.ConversionFormula;
import pojo.convert.ConversionNoFormula;
import pojo.convert.Conversions;
import pojo.database.ConversionDBHelper;

public class ConversionController {
    private String output = "";
    private Context context;

    public ConversionController(Context context) {
        this.context = context;
    }

    public void directConversion(double valueIn, String conversionType,
                                 String fromType, String toType) {
        ConversionDBHelper db = new ConversionDBHelper(this.context);
        ConversionNoFormula cnf = new ConversionNoFormula(this.context);
        ConversionFormula cf = new ConversionFormula(this.context);

        if(db.isFormula(fromType, toType).equals("yes")) {
            setConversionTypes(cf, fromType, toType);
            setOutput(cf.convert(valueIn));
        } else {
            setConversionTypes(cnf, fromType, toType);
            setOutput(cnf.convert(valueIn));
        }
    }

    private void setConversionTypes(Conversions obj, String fromType, String toType) {
        obj.setConversionType(fromType);
        obj.setConversionTypeTwo(toType);
    }

    private void setOutput(String outputValue) {
        this.output = outputValue;
    }

    public String getOutput() {
        return this.output;
    }
}
