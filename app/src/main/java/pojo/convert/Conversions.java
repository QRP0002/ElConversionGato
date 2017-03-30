package pojo.convert;

/**
 * Created by quinn on 3/21/17.
 */

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

    public abstract String convert(double valueIn);
}
