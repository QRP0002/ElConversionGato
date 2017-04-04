package pojo;

import pojo.convert.Conversions;
import pojo.convert.LengthConversion;
import pojo.convert.MassConversion;
import pojo.convert.SpeedConversion;
import pojo.convert.TemperatureConversion;

public class ConversionController {
    private String output = "";

    public void directConversion(double valueIn, String conversionType,
                                 String fromType, String toType) {
        switch (conversionType) {
            case "Length":
                LengthConversion lc = new LengthConversion();
                setConversionTypes(lc, fromType, toType);
                setOutput(lc.convert(valueIn));
                break;
            case "Mass":
                MassConversion mc = new MassConversion();
                setConversionTypes(mc, fromType, toType);
                setOutput(mc.convert(valueIn));
                break;
            case "Speed":
                SpeedConversion sc = new SpeedConversion();
                setConversionTypes(sc, fromType, toType);
                setOutput(sc.convert(valueIn));
                break;
            case "Temperature":
                TemperatureConversion tc = new TemperatureConversion();
                setConversionTypes(tc, fromType, toType);
                setOutput(tc.convert(valueIn));
                break;
            default:
                break;
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
