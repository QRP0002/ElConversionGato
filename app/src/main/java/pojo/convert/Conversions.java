package pojo.convert;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public abstract class Conversions {
    private String conversionType;
    private String conversionTypeTwo;

    String getConversionTypeTwo() {
        return conversionTypeTwo;
    }

    public void setConversionTypeTwo(String conversionTypeTwo) {
        this.conversionTypeTwo = conversionTypeTwo;
    }

    String getConversionType() {
        return conversionType;
    }

    public void setConversionType(String conversionType) {
        this.conversionType = conversionType;
    }

     String appendType(String inputStr) {
         DecimalFormat df = new DecimalFormat("#,##0.0#######################################");
         //df.setRoundingMode(RoundingMode.FLOOR);
         StringBuilder sb = new StringBuilder(df.format(Double.parseDouble(inputStr)));
         sb.append(" ");
         sb.append(conversionTypeTwo);
        return (sb.toString());
    }

    public abstract String convert(double valueIn);
}
